package persistence.sql.dml;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * INSERT 쿼리 생성
 */
public class InsertQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Query query;
    private final Object entity;

    public InsertQueryBuilder(Query query, Object entity) {
        validateEntityClass(entity.getClass());
        this.entityData = new EntityData(entity.getClass());
        this.query = query;
        this.entity = entity;
    }

    @Override
    public String getQuery() {
        return query.insertInto(entityData, entity);
    }

}
