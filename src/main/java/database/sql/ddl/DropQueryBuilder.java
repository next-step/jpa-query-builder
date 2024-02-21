package database.sql.ddl;

import database.sql.util.EntityClassInspector;

public class DropQueryBuilder {
    private final String tableName;

    public DropQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        this.tableName = inspector.getTableName();
    }

    public String buildQuery() {
        return String.format("DROP TABLE %s", tableName);
    }
}
