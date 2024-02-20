package persistence.sql.ddl;

import persistence.sql.domain.Table;

public class DropQueryBuilder {
    private static final String QUERY_TEMPLATE = "DROP TABLE %s";
    private final Table table;

    public DropQueryBuilder(Class<?> targetClass) {
        this.table = Table.of(targetClass);
    }

    public String build() {
        String tableName = table.getName();
        return String.format(QUERY_TEMPLATE, tableName);
    }
}
