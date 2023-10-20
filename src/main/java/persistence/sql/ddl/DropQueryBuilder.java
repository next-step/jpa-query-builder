package persistence.sql.ddl;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * DROP 쿼리 생성
 */
public class DropQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Query query;

    public DropQueryBuilder(Query query, Class<?> entityClass) {
        validateEntityClass(entityClass);
        this.entityData = new EntityData(entityClass);
        this.query = query;
    }

    @Override
    public String getQuery() {
        return query.drop(entityData);
    }

}
