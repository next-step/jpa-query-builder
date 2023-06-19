package persistence.ddl;


import persistence.Columns;
import persistence.Table;

public class SelectBuilder {
    private final Table table;
    private final Columns columns;

    public SelectBuilder(Table table, Columns columns) {
        this.table = table;
        this.columns = columns;
    }

    public String findAllQuery() {
        return String.format("SELECT * FROM %s", table.expression());
    }

    public String findById(long id) {
        return String.format("SELECT * FROM %s WHERE %s=%s", table.expression(), columns.primaryKey(), id);
    }
}
