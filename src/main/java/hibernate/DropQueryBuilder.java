package hibernate;

import hibernate.entity.EntityClass;

public class DropQueryBuilder implements QueryBuilder {

    private static final String DROP_TABLE_QUERY = "drop table %s";

    public DropQueryBuilder() {
    }

    @Override
    public String generateQuery(final Class<?> clazz) {
        EntityClass entity = new EntityClass(clazz);
        return String.format(DROP_TABLE_QUERY, entity.tableName());
    }
}
