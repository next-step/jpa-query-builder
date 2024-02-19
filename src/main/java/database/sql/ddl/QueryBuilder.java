package database.sql.ddl;

import database.sql.IQueryBuilder;

public class QueryBuilder {
    public String buildCreateQuery(Class<?> entityClass) {
        IQueryBuilder createQueryBuilder = new CreateQueryBuilder(entityClass);
        return createQueryBuilder.buildQuery();
    }

    public String buildDeleteQuery(Class<?> entityClass) {
        IQueryBuilder dropQueryBuilder = new DropQueryBuilder(entityClass);
        return dropQueryBuilder.buildQuery();
    }
}
