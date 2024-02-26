package persistence.sql.dml;

import persistence.sql.model.Column;
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

        PKColumn pkColumn = table.getPKColumn();
        String pkColumnName = pkColumn.getName();
        columnsClauseBuilder.append(pkColumnName);

        List<Column> columns = table.getColumns();
        columns.forEach(column -> {
            String name = column.getName();
            columnsClauseBuilder.append(',')
                    .append(name);
        });

        return columnsClauseBuilder.toString();
    }
}
