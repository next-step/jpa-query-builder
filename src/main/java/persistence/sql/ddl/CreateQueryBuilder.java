package persistence.sql.ddl;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * CREATE 쿼리 생성
 */
public class CreateQueryBuilder implements QueryBuilder {

    private final Query query;

    public CreateQueryBuilder(Query query) {
        this.query = query;
    }

    public String getQuery(Class<?> entityClass) {
        validateEntityClass(entityClass);
        return query.create(new EntityData(entityClass));
    }

}
