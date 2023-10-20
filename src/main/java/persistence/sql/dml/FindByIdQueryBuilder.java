package persistence.sql.dml;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * SELECT 쿼리 생성
 */
public class FindByIdQueryBuilder implements QueryBuilder {

    private final Query query;

    public FindByIdQueryBuilder(Query query) {
        this.query = query;
    }

    public String getQuery(Class<?> entityClass, Object id) {
        validateEntityClass(entityClass);
        return query.findById(new EntityData(entityClass), id);
    }

}
