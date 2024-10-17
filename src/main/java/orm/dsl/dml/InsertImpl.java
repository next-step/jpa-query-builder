package orm.dsl.dml;

import jakarta.persistence.GenerationType;
import orm.QueryRenderer;
import orm.TableEntity;
import orm.TableField;
import orm.dsl.QueryExecutor;
import orm.dsl.step.dml.InsertIntoStep;
import orm.exception.InvalidEntityException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InsertImpl<E> implements InsertIntoStep {

    private final QueryExecutor queryExecutor;

    // insert 할 테이블
    protected final TableEntity<E> tableEntity;

    // insert 할 컬럼들
    protected List<? extends TableField> inertFields;

    // insert 할 값들
    protected List<List<? extends TableField>> inertValues;

    public InsertImpl(TableEntity<E> tableEntity, QueryExecutor queryExecutor) {
        this.tableEntity = tableEntity;
        this.inertFields = extractInsertFields(tableEntity);
        this.queryExecutor = queryExecutor;
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
    public String build() {
        QueryRenderer queryRenderer = new QueryRenderer();
        var queryToken = List.of(
                "INSERT INTO",
                tableEntity.getTableName(),
                "(%s)".formatted(queryRenderer.singleColumnListDotted(inertFields)),
                "VALUES",
                "%s".formatted(queryRenderer.renderBulkInsertValues(inertValues))

        );

        return String.join(" ", queryToken);
    }

    protected String renderInsertValues() {
        List<String> result = new ArrayList<>(inertValues.size());
        for (List<? extends TableField> inertValue : inertValues) {
            result.add("(%s)".formatted(inertValue.stream()
                    .map(TableField::getFieldValue)
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")))
            );
        }

        return String.join(", ", result);
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
