package database.sql.ddl;

import database.sql.util.EntityClassInspector;

public class DropQueryBuilder {
    private final Class<?> entityClass;

    public DropQueryBuilder(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String buildQuery() {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();

        return String.format("DROP TABLE %s", tableName);
    }
}
