package persistence.sql.ddl;

import persistence.sql.meta.Table;

public class DropQueryBuilder {
    private static final String QUERY_TEMPLATE = "DROP TABLE IF EXISTS %s";

    private final Table table;

    public DropQueryBuilder(Class<?> entityClass) {
        this.table = new Table(entityClass);
    }

    public String drop() {
        return table.getQuery(QUERY_TEMPLATE, table.getTableName());
    }
}
