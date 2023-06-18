package persistence.ddl;

import persistence.Table;

public class DeleteBuilder {
    private static final String KEYWORD = "DELETE FROM %s WHERE %s = %s";
    private final Table table;

    public DeleteBuilder(Table table) {
        this.table = table;
    }

    public String query(String column, Object value) {
        return String.format(KEYWORD, table.expression(), column, value).toUpperCase();
    }
}
