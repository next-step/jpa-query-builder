package persistence.sql.dml;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * SELECT 쿼리 생성
 */
public class FindByIdQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Query query;
    private final Object entity;

    public FindByIdQueryBuilder(Query query, Object entity) {
        validateEntityClass(entity.getClass());
        this.entityData = new EntityData(entity.getClass());
        this.query = query;
        this.entity = entity;
    }

    @Override
    public String getQuery() {
        return query.getFindByIdQuery(entityData, entity);
    }

}
