package database.sql.ddl;

public class QueryBuilder {
    public String buildCreateQuery(Class<?> entityClass) {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(entityClass);
        return createQueryBuilder.buildQuery();
    }

    public String buildDeleteQuery(Class<?> entityClass) {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(entityClass);
        return dropQueryBuilder.buildQuery();
    }
}
