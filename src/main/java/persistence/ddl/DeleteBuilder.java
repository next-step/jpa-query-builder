package persistence.ddl;

import persistence.Table;

public class DeleteBuilder {
    private final Table table;

    public DeleteBuilder(Table table) {
        this.table = table;
    }

    public String query(String column, Object value) {
        return String.format("DELETE FROM %s WHERE %s = %s", table.expression(), column, value).toUpperCase();
    }
}
