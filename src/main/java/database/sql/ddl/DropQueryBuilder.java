package database.sql.ddl;

import database.sql.util.EntityClassInspector;

public class DropQueryBuilder {
    private final String query;

    public DropQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        query = String.format("DROP TABLE %s", tableName);
    }

    public String buildQuery() {
        return query;
    }
}
