package persistence.sql.ddl;

import persistence.common.EntityClazz;
import persistence.sql.QueryBuilder;

public class DropQueryBuilder implements QueryBuilder {
    @Override
    public String build(Object obj) {
        EntityClazz entityClazz = new EntityClazz(obj.getClass());
        return entityClazz.getDropQuery() + ";";
    }
}
