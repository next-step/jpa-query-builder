package persistence.ddl;

import persistence.ColumnMap;
import persistence.Table;

public class InsertBuilder {
    private static final String KEYWORD = "insert into %s (%s) values (%s)";
    private final ColumnMap columnMap;
    private final Table table;

    public InsertBuilder(Table table, ColumnMap columnMap) {
        this.columnMap = columnMap;
        this.table = table;
    }

    public String query() {
        return String.format(KEYWORD,
                table.expression(),
                String.join(",", columnMap.names()),
                columnMap.values());
    }

}
