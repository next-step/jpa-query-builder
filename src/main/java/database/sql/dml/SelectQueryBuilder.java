package database.sql.dml;

import database.sql.util.EntityClassInspector;

public class SelectQueryBuilder {
    private final String tableName;
    private final String fieldsForSelecting;

    public SelectQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        this.tableName = inspector.getTableName();
        this.fieldsForSelecting = inspector.getJoinedColumnNames();
    }

    public String buildQuery() {
        return String.format("SELECT %s FROM %s", fieldsForSelecting, tableName);
    }
}
