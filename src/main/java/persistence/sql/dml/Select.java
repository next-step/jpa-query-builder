package persistence.sql.dml;

import persistence.sql.mapping.Table;

import java.util.List;

public class Select {

    private final Table table;

    private List<Where> whereClause = List.of();

    public Select(Table table) {
        this.table = table;
    }

    public Select(Table table, List<Where> whereClause) {
        this(table);
        this.whereClause = whereClause;
    }

    public Table getTable() {
        return table;
    }

    public List<Where> getWhereClause() {
        return whereClause;
    }
}
