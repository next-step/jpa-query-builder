package persistence.ddl;

import persistence.Columns;
import persistence.Table;

public class CreateTableBuilder {
    private static final String KEYWORD = "create table %s (%s);";
    private final Table table;
    private final Columns columns;

    public CreateTableBuilder(Table table, Columns columns) {
        this.table = table;
        this.columns = columns;
    }

    public String query() {
        return String.format(KEYWORD, table.expression(), columns.expression());
    }
}
