package persistence.sql.ddl;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * CREATE 쿼리 생성
 */
public class CreateQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Query query;

    public CreateQueryBuilder(Query query, Class<?> entityClass) {
        validateEntityClass(entityClass);
        this.entityData = new EntityData(entityClass);
        this.query = query;
    }

    @Override
    public String getQuery() {
        return query.getCreateQuery(entityData);
    }

}
