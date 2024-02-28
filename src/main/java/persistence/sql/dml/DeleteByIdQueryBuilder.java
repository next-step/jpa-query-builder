package persistence.sql.dml;

import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

public class DeleteByIdQueryBuilder {

    private final static String DELETE_BY_ID_QUERY_FORMAT = "DELETE FROM %s WHERE %s;";

    private final Table table;
    private final Object id;

    public DeleteByIdQueryBuilder(Table table, Object id) {
        this.table = table;
        this.id = id;
    }

    public String build() {
        String tableName = table.getName();
        String whereClause = buildWhereClause();
        return String.format(DELETE_BY_ID_QUERY_FORMAT, tableName, whereClause);
    }

    private String buildWhereClause() {
        StringBuilder whereClauseBuilder = new StringBuilder();

        String pkColumnName = table.getPKColumnName();
        whereClauseBuilder.append(pkColumnName)
                .append('=')
                .append(id);

        return whereClauseBuilder.toString();
    }
}
