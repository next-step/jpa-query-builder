package persistence.sql.dml;

import persistence.sql.Query;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * delete 쿼리 생성
 */
public class DeleteQueryBuilder implements QueryBuilder {

    private final Query query;

    public DeleteQueryBuilder(Query query) {
        this.query = query;
    }

    public String getQuery(Object entity) {
        validateEntityClass(entity.getClass());
        return query.delete(new EntityData(entity.getClass()), entity);
    }

}
