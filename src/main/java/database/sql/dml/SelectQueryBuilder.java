package database.sql.dml;

import database.sql.util.EntityClassInspector;
import database.sql.util.column.Column;

import java.util.stream.Collectors;

public class SelectQueryBuilder {
    private final String query;

    public SelectQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        String fieldsForSelecting = inspector.getColumns()
                .map(Column::getColumnName)
                .collect(Collectors.joining(", "));
        query = String.format("SELECT %s FROM %s", fieldsForSelecting, tableName);
    }

    public String buildQuery() {
        return query;
    }
}
