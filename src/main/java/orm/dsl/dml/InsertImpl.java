package orm.dsl.dml;

import jakarta.persistence.GenerationType;
import orm.dsl.QueryRenderer;
import orm.TableEntity;
import orm.TableField;
import orm.dsl.QueryRunner;
import orm.dsl.step.dml.InsertIntoStep;
import orm.exception.InvalidEntityException;
import orm.exception.OrmPersistenceException;
import orm.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class InsertImpl<E> implements InsertIntoStep {

    private final QueryRunner queryRunner;

    // insert 할 테이블
    protected final TableEntity<E> tableEntity;

    // insert 할 컬럼들
    protected List<? extends TableField> inertFields;

    // insert 할 값들
    protected List<List<? extends TableField>> inertValues;

    public InsertImpl(TableEntity<E> tableEntity, QueryRunner queryRunner) {
        this.tableEntity = tableEntity;
        this.inertFields = extractInsertFields(tableEntity);
        this.queryRunner = queryRunner;
    }

    @Override
    public <T> InsertIntoStep values(T entity) {
        throwIfNotMatchingEntity(tableEntity, entity);
        this.inertValues = List.of(extractInsertValues(entity));
        return this;
    }

    @Override
    public <T> InsertIntoStep values(List<T> entityList) {
        for (T entity : entityList) {
            throwIfNotMatchingEntity(tableEntity, entity);
        }

        this.inertValues = entityList.stream()
                .map(this::extractInsertValues)
                .collect(Collectors.toUnmodifiableList());

        return this;
    }

    @Override
    public String extractSql() {
        QueryRenderer queryRenderer = new QueryRenderer();
        final String bulkInsertValues = queryRenderer.renderBulkInsertValues(inertValues);
        final String joinColumnsWithComma = queryRenderer.joinColumnNamesWithComma(inertFields);

        if (StringUtils.isBlank(bulkInsertValues)) {
            throw new OrmPersistenceException("insert 할 값이 없습니다.");
        }

        var queryToken = List.of(
                "INSERT INTO",
                tableEntity.getTableName(),
                "(%s)".formatted(joinColumnsWithComma),
                "VALUES",
                "%s".formatted(bulkInsertValues)

        );

        return String.join(" ", queryToken);
    }

    @Override
    public void execute() {
        queryRunner.execute(extractSql());
    }

    /**
     * Auto Increment 인 경우 ID 컬럼 포함.
     * 그 외의 경우 ID 컬럼 제외하고 insert 할 컬럼들 추출
     */
    private <T> List<? extends TableField> extractInsertValues(T entity) {
        TableEntity<T> tableEntity = new TableEntity<>(entity);
        return tableEntity.getIdGenerationType() == GenerationType.IDENTITY
                ? tableEntity.getNonIdFields()
                : tableEntity.getAllFields();
    }

    private <T> List<? extends TableField> extractInsertFields(TableEntity<T> tableEntity ) {
        return tableEntity.getIdGenerationType() == GenerationType.IDENTITY
                ? tableEntity.getNonIdFields()
                : tableEntity.getAllFields();
    }

    private <T> void throwIfNotMatchingEntity(TableEntity<E> tableEntity, T entityClass) {
        if (tableEntity.getTableClass() != entityClass.getClass()) {
            throw new InvalidEntityException("insert 할 수 없는 엔티티 클래스입니다.");
        }
    }
}
