package persistence.sql.ddl;

import persistence.sql.QueryBuilder;

public class DropQueryBuilder extends QueryBuilder {
    private static final String DROP_QUERY_TEMPLATE = "DROP TABLE IF EXISTS %s";

    public DropQueryBuilder(Object entity) {
        super(entity);
    }

    @Override
    public String build() {
        return super.build(DROP_QUERY_TEMPLATE);
    }
}
