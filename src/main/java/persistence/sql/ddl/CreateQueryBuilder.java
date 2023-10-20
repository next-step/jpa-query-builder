package persistence.sql.ddl;

import persistence.sql.Dialect;
import persistence.sql.QueryBuilder;
import persistence.sql.entity.EntityData;

/**
 * CREATE 쿼리 생성
 */
public class CreateQueryBuilder implements QueryBuilder {

    private final EntityData entityData;
    private final Dialect dialect;

    public CreateQueryBuilder(Dialect dialect, Class<?> entityClass) {
        validateEntityClass(entityClass);
        this.entityData = new EntityData(entityClass);
        this.dialect = dialect;
    }

    @Override
    public String getQuery() {
        return dialect.getCreateQuery(entityData);
    }

}
