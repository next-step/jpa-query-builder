package persistence.sql.ddl;

import persistence.sql.domain.Table;

public class DropQueryBuilder {
    private static final String DROP_QUERY_TEMPLATE = "DROP TABLE %s";
    private final Table table;

    public DropQueryBuilder(Class<?> target) {
        this.table = Table.of(target);
    }

    public String build() {
        return String.format(DROP_QUERY_TEMPLATE, table.getName());
    }
}
