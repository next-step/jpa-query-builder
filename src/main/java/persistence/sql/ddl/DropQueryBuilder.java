package persistence.sql.ddl;

import persistence.sql.model.Table;

public class DropQueryBuilder {

    private final static String DROP_QUERY_FORMAT = "DROP TABLE %s;";

    private final Table table;

    public DropQueryBuilder(Table table) {
        this.table = table;
    }

    public String build() {
        String tableName = table.getName();
        return String.format(DROP_QUERY_FORMAT, tableName);
    }
}
