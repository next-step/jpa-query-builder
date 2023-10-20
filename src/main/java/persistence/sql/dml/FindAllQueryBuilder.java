package persistence.sql.dml;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * SELECT 쿼리 생성
 */
public class FindAllQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Query query;

    public FindAllQueryBuilder(Query query, Class<?> entityClass) {
        validateEntityClass(entityClass);
        this.entityData = new EntityData(entityClass);
        this.query = query;
    }

    @Override
    public String getQuery() {
        return query.getFindAllQuery(entityData);
    }

}
