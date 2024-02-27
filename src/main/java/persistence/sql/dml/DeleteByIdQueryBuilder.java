package persistence.sql.dml;

import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

public class DeleteByIdQueryBuilder {

    private final static String DELETE_BY_ID_QUERY_FORMAT = "DELETE FROM %s WHERE %s;";

    private final Table table;
    private final Object instance;

    public DeleteByIdQueryBuilder(Table table, Object instance) {
        this.table = table;
        this.instance = instance;
    }

    public String build() {
        String tableName = table.getName();
        String whereClause = buildWhereClause();
        return String.format(DELETE_BY_ID_QUERY_FORMAT, tableName, whereClause);
    }

    private String buildWhereClause() {
        StringBuilder whereClauseBuilder = new StringBuilder();

        PKColumn pkColumn = table.getPKColumn();

        String pkColumnName = pkColumn.getName();
        Object value = pkColumn.getValue(instance);

        whereClauseBuilder.append(pkColumnName)
                .append('=')
                .append(value);

        return whereClauseBuilder.toString();
    }
}
