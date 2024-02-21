package persistence.sql.dml;

import persistence.sql.domain.Table;

public class SelectAllQueryBuilder {
    private static final String FIND_ALL_QUERY_TEMPLATE = "SELECT * FROM %s";

    private final Table table;

    public SelectAllQueryBuilder(Class<?> clazz) {
        this.table = Table.of(clazz);
    }

    public String build() {
        return String.format(FIND_ALL_QUERY_TEMPLATE, table.getName());
    }
}
