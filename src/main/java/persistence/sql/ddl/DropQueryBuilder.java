package persistence.sql.ddl;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * DROP 쿼리 생성
 */
public class DropQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Dialect dialect;

    public DropQueryBuilder(Dialect dialect, Class<?> entityClass) {
        validateEntityClass(entityClass);
        this.entityData = new EntityData(entityClass);
        this.dialect = dialect;
    }

    @Override
    public String getQuery() {
        return dialect.getDropQuery(entityData);
    }

}
