package orm.dsl.dml;

import jakarta.persistence.GenerationType;
import orm.dsl.QueryRenderer;
import orm.TableEntity;
import orm.TableField;
import orm.dsl.QueryRunner;
import orm.dsl.step.dml.BulkInsertIntoValuesStep;
import orm.dsl.step.dml.InsertIntoStep;
import orm.dsl.step.dml.InsertIntoValuesStep;
import orm.exception.InvalidEntityException;
import orm.exception.OrmPersistenceException;
import orm.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InsertImpl<E> implements InsertIntoStep<E> {

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
    public InsertIntoValuesStep<E> value(E entity) {
        throwIfNotMatchingEntity(tableEntity, entity);
        this.inertValues = List.of(extractInsertValues(entity));
        return this;
    }

    /**
     * Bulk Insert는 values() 중복실행 가능
     * @param entityList insert 할 엔티티 리스트
     * @param <T> 엔티티
     */
    @Override
    public <T> BulkInsertIntoValuesStep values(List<T> entityList) {
        for (T entity : entityList) {
            throwIfNotMatchingEntity(tableEntity, entity);
        }

        final List<List<? extends TableField>> newEntityList = entityList.stream()
                .map(this::extractInsertValues)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(newEntityList)) {
            this.inertValues = newEntityList;
            return this;
        }

        if(this.inertValues == null) {
            this.inertValues = new ArrayList<>();
        }

        this.inertValues.addAll(newEntityList);
        return this;
    }

    /**
     * 사용된 엔티티를 Auto Increment가 존재하면 포함해서 반환
     * @return 엔티티 클래스
     */
    @Override
    public E returnAsEntity() {
        Object idValue = queryRunner.executeUpdateWithReturningKey(extractSql());
        tableEntity.replaceAllFields(inertValues.getFirst());

        if (tableEntity.hasDbGeneratedId()) {
            tableEntity.setIdValue(idValue);
        }

        tableEntity.syncFieldValueToEntity();
        return tableEntity.getEntity();
    }

    @Override
    public String extractSql() {
        throwIfNoInsertValues(inertValues);
        QueryRenderer queryRenderer = new QueryRenderer();
        final String bulkInsertValues = queryRenderer.renderBulkInsertValues(inertValues);
        final String joinColumnsWithComma = queryRenderer.joinColumnNamesWithComma(inertFields);

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
        throwIfNoInsertValues(inertValues);
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

    private void throwIfNoInsertValues(List<List<? extends TableField>> inertValues) {
        if (CollectionUtils.isEmpty(inertValues)) {
            throw new OrmPersistenceException("insert 할 값이 없습니다.");
        }
    }
}
