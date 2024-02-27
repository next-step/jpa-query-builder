package persistence.sql.dml;

import persistence.sql.model.Table;

public class DeleteAllQueryBuilder {

    private final static String DELETE_ALL_QUERY_FORMAT = "DELETE FROM %s;";

    private final Table table;

    public DeleteAllQueryBuilder(Table table) {
        this.table = table;
    }

    public String build() {
        String tableName = table.getName();
        return String.format(DELETE_ALL_QUERY_FORMAT, tableName);
    }
}
