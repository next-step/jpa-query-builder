package database.sql.dml;

import database.sql.util.EntityClassInspector;
import database.sql.util.column.Column;

import java.util.stream.Collectors;

public class SelectOneQueryBuilder {
    private final String queryPart;

    public SelectOneQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        String fieldsForSelecting = inspector.getColumns()
                .map(Column::getColumnName)
                .collect(Collectors.joining(", "));
        String primaryKeyColumnName = inspector.getPrimaryKeyColumn().getColumnName();

        this.queryPart = String.format("SELECT %s FROM %s WHERE %s = ", fieldsForSelecting, tableName, primaryKeyColumnName);
    }

    public String buildQuery(Long id) {
        return queryPart + id;
    }
}
