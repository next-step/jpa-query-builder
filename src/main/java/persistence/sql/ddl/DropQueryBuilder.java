package persistence.sql.ddl;

public class DropQueryBuilder extends QueryBuilder {
    private static final String DROP_QUERY_TEMPLATE = "DROP TABLE IF EXISTS %s";

    public DropQueryBuilder(Class<?> entityClass) {
        super(entityClass);
    }

    @Override
    public String build() {
        return super.build(DROP_QUERY_TEMPLATE);
    }
}
