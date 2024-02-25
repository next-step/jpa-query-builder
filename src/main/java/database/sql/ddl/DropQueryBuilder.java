package database.sql.ddl;

import database.sql.util.EntityMetadata;

public class DropQueryBuilder {
    private final String tableName;

    public DropQueryBuilder(Class<?> entityClass) {
        EntityMetadata metadata = new EntityMetadata(entityClass);
        this.tableName = metadata.getTableName();
    }

    public String buildQuery() {
        return String.format("DROP TABLE %s", tableName);
    }
}
