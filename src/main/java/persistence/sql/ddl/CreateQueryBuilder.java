package persistence.sql.ddl;

import persistence.sql.domain.Table;

public class CreateQueryBuilder {
    private static final String CREATE_QUERY_TEMPLATE = "CREATE TABLE %s (%s)";

    private final Table table;

    public CreateQueryBuilder(Class<?> targetClass) {
        this.table = Table.of(targetClass);
    }

    public String build() {
        String tableName = table.getName();
        String tableFields = table.getFieldQueries();
        return String.format(CREATE_QUERY_TEMPLATE, tableName, tableFields);
    }
}
