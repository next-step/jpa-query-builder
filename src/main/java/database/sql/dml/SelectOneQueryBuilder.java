package database.sql.dml;

import database.sql.util.EntityClassInspector;

public class SelectOneQueryBuilder {
    private final String tableName;
    private final String fieldsForSelecting;
    private final String primaryKeyColumnName;

    public SelectOneQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        this.tableName = inspector.getTableName();
        this.fieldsForSelecting = inspector.getJoinedColumnNames();
        this.primaryKeyColumnName = inspector.getPrimaryKeyColumnName();
    }

    public String buildQuery(Long id) {
        return String.format("SELECT %s FROM %s WHERE %s = %d", fieldsForSelecting, tableName, primaryKeyColumnName, id);
    }
}
