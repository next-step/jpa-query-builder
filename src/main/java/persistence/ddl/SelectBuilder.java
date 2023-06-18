package persistence.ddl;


import persistence.Table;

public class SelectBuilder {
    private final Table table;

    public SelectBuilder(Table table) {
        this.table = table;
    }

    public String findAllQuery() {
        return String.format("SELECT * FROM %s", table.expression());
    }
}
