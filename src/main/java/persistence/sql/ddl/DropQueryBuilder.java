package persistence.sql.ddl;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * DROP 쿼리 생성
 */
public class DropQueryBuilder implements QueryBuilder {

    private final Query query;

    public DropQueryBuilder(Query query) {
        this.query = query;
    }

    public String getQuery(Class<?> entityClass) {
        validateEntityClass(entityClass);
        return query.drop(new EntityData(entityClass));
    }

}
