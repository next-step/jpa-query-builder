package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.wrapper.Table;

public class DropQueryBuilder implements QueryBuilder {

    private static final String DROP_TABLE_DEFINITION = "DROP TABLE %s";

    private DropQueryBuilder() {
    }

    public static DropQueryBuilder from() {
        return new DropQueryBuilder();
    }

    @Override
    public String generateQuery(Class<?> clazz) {
        Table table = Table.of(clazz);
        return String.format(DROP_TABLE_DEFINITION, table.getTableName());
    }
}
