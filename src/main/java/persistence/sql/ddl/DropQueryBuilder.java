package persistence.sql.ddl;

import persistence.sql.QueryBuilder;
import persistence.sql.ddl.wrapper.Table;

public class DropQueryBuilder implements QueryBuilder {

    private static DropQueryBuilder instance = null;

    private static final String DROP_TABLE_DEFINITION = "DROP TABLE %s";

    private DropQueryBuilder() {
    }

    public static synchronized DropQueryBuilder getInstance() {
        if (instance == null) {
            instance = new DropQueryBuilder();
        }
        return instance;
    }

    @Override
    public String builder(Class<?> clazz) {
        Table table = Table.of(clazz);
        return String.format(DROP_TABLE_DEFINITION, table.getTableName());
    }
}
