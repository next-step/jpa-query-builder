package persistence.sql.dml;

import persistence.sql.dbms.Dialect;
import persistence.sql.entitymetadata.model.EntityColumn;

import java.util.List;
import java.util.stream.Collectors;

public class InsertDMLQueryBuilder<E> extends DMLQueryBuilder<E> {

    private E entity;
    private List<String> defaultValuesClause;

    public InsertDMLQueryBuilder(Dialect dialect, E entity) {
        super(dialect, (Class<E>) entity.getClass());
        this.entity = entity;
        this.defaultValuesClause = initDefaultValuesClause();
    }

    @Override
    public String build() {
        return String.format("INSERT INTO %s %s VALUES %s;",
                createTableNameDefinition(),
                createInsertColumnsClause(),
                createInsertValueClause());
    }

    private List<String> initDefaultValuesClause() {
        return entityTable.getColumns()
                .stream()
                .map(this::getEntityColumnValue)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    private <V> V getEntityColumnValue(EntityColumn<E, V> entityColumn) {
        if (entityColumn.getType() == String.class) {
            return (V) ("'" + entityColumn.getValue(entity) + "'");
        }

        return entityColumn.getValue(entity);
    }

    private String createInsertColumnsClause() {
       return "(" + String.join(", ", defaultColumnsClause) + ")";
    }

    private String createInsertValueClause() {
        return "(" + String.join(", ", defaultValuesClause) + ")";
    }
}
