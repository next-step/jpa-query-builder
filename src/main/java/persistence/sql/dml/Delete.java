package persistence.sql.dml;

import persistence.sql.mapping.Table;

import java.util.List;

public class Delete {

    private final Table table;

    private List<Where> whereClause = List.of();

    public Delete(final Table table) {
        this.table = table;
    }

    public Delete(final Table table, final List<Where> whereClause) {
        this.table = table;
        this.whereClause = whereClause;
    }

    public Table getTable() {
        return table;
    }

    public List<Where> getWhereClause() {
        return whereClause;
    }
}
