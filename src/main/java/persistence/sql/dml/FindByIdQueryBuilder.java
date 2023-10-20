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
    private final Object id;

    public FindByIdQueryBuilder(Query query, Class<?> entityClass, Object id) {
        validateEntityClass(entityClass);
        this.entityData = new EntityData(entityClass);
        this.query = query;
        this.id = id;
    }

    @Override
    public String getQuery() {
        return query.getFindByIdQuery(entityData, id);
    }

}
