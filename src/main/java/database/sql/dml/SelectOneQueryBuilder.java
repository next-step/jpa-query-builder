package database.sql.dml;

import database.sql.util.EntityClassInspector;
import database.sql.util.column.IColumn;

import java.util.stream.Collectors;

public class SelectOneQueryBuilder {
    private final Class<?> entityClass;
    private final Long id;

    public SelectOneQueryBuilder(Class<?> entityClass, Long id) {
        this.entityClass = entityClass;
        this.id = id;
    }

    public String buildQuery() {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        String fieldsForSelecting = inspector.getColumns()
                .map(IColumn::getColumnName)
                .collect(Collectors.joining(", "));
        String whereClause = whereClause(inspector);

        return String.format("SELECT %s FROM %s WHERE %s", fieldsForSelecting, tableName, whereClause);
    }

    private String whereClause(EntityClassInspector inspector) {
        IColumn primaryKeyColumn = inspector.getPrimaryKeyColumn();
        String fieldName = primaryKeyColumn.getColumnName();
        return fieldName + " = " + id;
    }
}
