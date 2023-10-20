package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * INSERT 쿼리 생성
 */
public class InsertQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Dialect dialect;
    private final Object entity;

    public InsertQueryBuilder(Dialect dialect, Object entity) {
        validateEntityClass(entity.getClass());
        this.entityData = new EntityData(entity.getClass());
        this.dialect = dialect;
        this.entity = entity;
    }

    @Override
    public String getQuery() {
        return dialect.getInsertQuery(entityData, entity);
    }

}
