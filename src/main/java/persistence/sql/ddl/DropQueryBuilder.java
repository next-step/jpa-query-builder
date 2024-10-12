package persistence.sql.ddl;

import persistence.sql.QueryBuilder;

public class DropQueryBuilder extends QueryBuilder {
    private static final String QUERY_TEMPLATE = "DROP TABLE IF EXISTS %s";

    public DropQueryBuilder(Class<?> entityClass) {
        super(entityClass);
    }

    public String drop() {
        return super.build(QUERY_TEMPLATE, getTableName());
    }
}
