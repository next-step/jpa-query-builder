package orm.dsl.dml;

import jakarta.persistence.GenerationType;
import orm.TableEntity;
import orm.TableField;
import orm.dsl.step.dml.InsertIntoStep;
import orm.exception.InvalidEntityException;

import java.util.List;
import java.util.stream.Collectors;

public abstract class InsertImpl<E> implements InsertIntoStep {

    // insert 할 테이블
    protected final TableEntity<E> tableEntity;

    // insert 할 컬럼들
    protected List<? extends TableField> inertFields;

    public InsertImpl(TableEntity<E> tableEntity) {
        this.tableEntity = tableEntity;
    }

    @Override
    public <T> InsertIntoStep values(T entity) {
        throwIfNotMatchingEntity(tableEntity, entity);
        this.inertFields = extractInsertFields();
        return this;
    }

    @Override
    public <T> InsertIntoStep values(List<T> entityList) {
        for (T entity : entityList) {
            throwIfNotMatchingEntity(tableEntity, entity);
        }

        this.inertFields = extractInsertFields();
        return this;
    }

    @Override
    public String build() {
        var queryToken = List.of(
                "INSERT INTO",
                tableEntity.getTableName(),
                "(%s)".formatted(insertColumns()),
                "VALUES",
                "(%s)".formatted(""/*insertValues()*/)

        );

        return String.join(" ", queryToken);
    }

    /*private String insertValues() {
        return inertFields.stream()
                .map(TableField::getFieldValue)
                .collect(Collectors.joining(","));
    }*/

    protected String insertColumns() {
        return inertFields.stream()
                .map(TableField::getFieldName)
                .collect(Collectors.joining(","));
    }

    /**
     * Auto Increment 인 경우 ID 컬럼 포함.
     * 그 외의 경우 ID 컬럼 제외하고 insert 할 컬럼들 추출
     */
    private List<? extends TableField> extractInsertFields() {
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
