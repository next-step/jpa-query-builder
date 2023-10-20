package persistence.sql.dml;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * SELECT 쿼리 생성
 */
public class FindAllQueryBuilder implements QueryBuilder {

    private final Query query;

    public FindAllQueryBuilder(Query query) {
        this.query = query;
    }

    public String getQuery(Class<?> entityClass) {
        validateEntityClass(entityClass);
        return query.findAll(new EntityData(entityClass));
    }

}
