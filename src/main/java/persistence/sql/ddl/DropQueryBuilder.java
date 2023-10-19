package persistence.sql.ddl;

import persistence.sql.QueryBuilder;

public class DropQueryBuilder implements QueryBuilder {
    @Override
    public String build(Object obj) {
        EntityQueryBuilder entityQueryBuilder = new EntityQueryBuilder(obj.getClass());
        return entityQueryBuilder.getDropQuery() + ";";
    }
}
