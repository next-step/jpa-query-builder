package persistence.sql.dml;

import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

import java.util.List;

public class FindAllQueryBuilder {

    private final static String FIND_ALL_QUERY_FORMAT = "SELECT %s FROM %s;";

    private final Table table;

    public FindAllQueryBuilder(Table table) {
        this.table = table;
    }

    public String build() {
        String columnsClause = buildColumnsClause();
        String tableName = table.getName();
        return String.format(FIND_ALL_QUERY_FORMAT, columnsClause, tableName);
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
}
