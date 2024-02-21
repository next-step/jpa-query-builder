package database.sql.ddl;

public class QueryBuilder {
    private static final QueryBuilder INSTANCE = new QueryBuilder();

    private QueryBuilder() {
    }

    public static QueryBuilder getInstance() {
        return INSTANCE;
    }

    public String buildCreateQuery(Class<?> entityClass) {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(entityClass);
        return createQueryBuilder.buildQuery();
    }

    public String buildDeleteQuery(Class<?> entityClass) {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(entityClass);
        return dropQueryBuilder.buildQuery();
    }
}
