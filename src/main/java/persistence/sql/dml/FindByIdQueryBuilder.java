package persistence.sql.dml;

import persistence.sql.model.Column;
import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

import java.util.List;

public class FindByIdQueryBuilder {

    private final static String FIND_BY_ID_QUERY_FORMAT = "SELECT %s FROM %s WHERE %s;";

    private final Table table;
    private final Object id;

    public FindByIdQueryBuilder(Table table, Object id) {
        this.table = table;
        this.id = id;
    }

    public String build() {
        String columnsClause = buildColumnsClause();
        String tableName = table.getName();
        String whereClause = buildWhereClause();
        return String.format(FIND_BY_ID_QUERY_FORMAT, columnsClause, tableName, whereClause);
    }

    private String buildColumnsClause() {
        StringBuilder columnsClauseBuilder = new StringBuilder();

        String pkColumnName = table.getPKColumnName();
        columnsClauseBuilder.append(pkColumnName)
                .append(',');

        List<String> columnNames = table.getColumnNames();
        String joinedColumnNames = String.join(",", columnNames);
        columnsClauseBuilder.append(joinedColumnNames);

        return columnsClauseBuilder.toString();
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
