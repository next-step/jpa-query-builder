package hibernate.ddl;

import hibernate.entity.EntityClass;

public class DropQueryBuilder {

    private static final String DROP_TABLE_QUERY = "drop table %s";

    public DropQueryBuilder() {
    }

    public String generateQuery(final EntityClass entity) {
        return String.format(DROP_TABLE_QUERY, entity.tableName());
    }
}
