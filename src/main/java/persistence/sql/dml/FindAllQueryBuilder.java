package persistence.sql.dml;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * SELECT 쿼리 생성
 */
public class FindAllQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Dialect dialect;

    public FindAllQueryBuilder(Dialect dialect, Class<?> entityClass) {
        validateEntityClass(entityClass);
        this.entityData = new EntityData(entityClass);
        this.dialect = dialect;
    }

    @Override
    public String getQuery() {
        return dialect.getFindAllQuery(entityData);
    }

}
