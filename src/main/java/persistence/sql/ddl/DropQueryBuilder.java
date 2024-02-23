package persistence.sql.ddl;

import persistence.sql.domain.Table;

public class DropQueryBuilder {
    private static final String DROP_QUERY_TEMPLATE = "DROP TABLE %s";

    public String build(Class<?> target) {
        Table table = Table.from(target);
        return String.format(DROP_QUERY_TEMPLATE, table.getName());
    }
}
