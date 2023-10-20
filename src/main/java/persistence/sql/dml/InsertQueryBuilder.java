package persistence.sql.dml;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * INSERT 쿼리 생성
 */
public class InsertQueryBuilder implements QueryBuilder {

    private final Query query;

    public InsertQueryBuilder(Query query) {
        this.query = query;
    }

    public String getQuery(Object entity) {
        validateEntityClass(entity.getClass());
        return query.insert(new EntityData(entity.getClass()), entity);
    }

}
