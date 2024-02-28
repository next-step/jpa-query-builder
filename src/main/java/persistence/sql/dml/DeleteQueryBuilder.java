package persistence.sql.dml;

import persistence.sql.model.Table;

public class DeleteQueryBuilder {

    private final static String DELETE_ALL_QUERY_FORMAT = "DELETE FROM %s;";
    private final static String DELETE_BY_ID_QUERY_FORMAT = "DELETE FROM %s WHERE %s;";

    private final Table table;

    public DeleteQueryBuilder(Table table) {
        this.table = table;
    }

    public String build() {
        String tableName = table.getName();
        return String.format(DELETE_ALL_QUERY_FORMAT, tableName);
    }

    public String buildById(Object id) {
        ByIdQueryBuilder byIdQueryBuilder = new ByIdQueryBuilder(table, id);

        String tableName = table.getName();
        String whereClause = byIdQueryBuilder.build();
        return String.format(DELETE_BY_ID_QUERY_FORMAT, tableName, whereClause);
    }
}
