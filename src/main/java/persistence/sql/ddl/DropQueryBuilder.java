package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.meta.Table;

public class DropQueryBuilder implements QueryBuilder {

    private static final String DROP_TABLE_DEFINITION = "DROP TABLE %s";

    private DropQueryBuilder() {
    }

    public static DropQueryBuilder from() {
        return new DropQueryBuilder();
    }

    @Override
    public String generateQuery(Object object) {
        Table table = Table.from((Class<?>) object);
        return String.format(DROP_TABLE_DEFINITION, table.getTableName());
    }
}
