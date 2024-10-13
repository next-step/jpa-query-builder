package persistence.sql.ddl;

import persistence.sql.meta.EntityTable;

public class DropQueryBuilder {
    private static final String QUERY_TEMPLATE = "DROP TABLE IF EXISTS %s";

    private final EntityTable entityTable;

    public DropQueryBuilder(Class<?> entityClass) {
        this.entityTable = new EntityTable(entityClass);
    }

    public String drop() {
        return entityTable.getQuery(QUERY_TEMPLATE, entityTable.getTableName());
    }
}
