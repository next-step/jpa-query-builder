package persistence.ddl;

import persistence.Table;

public class DeleteTableBuilder {
    private static final String KEYWORD = "drop table if exists %s cascade";
    private final Table table;

    public DeleteTableBuilder(Table table) {
        this.table = table;
    }

    public String query() {
        return String.format(KEYWORD, table.expression());
    }

}
