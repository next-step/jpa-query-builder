package persistence.sql.dml;

import persistence.sql.model.Table;

public class DeleteQueryBuilder {

    private final static String DELETE_QUERY_FORMAT = "DELETE FROM %s;";

    private final Table table;

    public DeleteQueryBuilder(Table table) {
        this.table = table;
    }

    public String build() {
        String tableName = table.getName();
        return String.format(DELETE_QUERY_FORMAT, tableName);
    }
}
