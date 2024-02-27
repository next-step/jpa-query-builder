package persistence.sql.dml;

import persistence.sql.model.Column;
import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

import java.util.List;

public class InsertQueryBuilder {

    private final static String INSERT_QUERY_FORMAT = "INSERT INTO %s (%s) values (%s);";

    private final Table table;
    private final Object instance;

    public InsertQueryBuilder(Table table, Object instance) {
        this.table = table;
        this.instance = instance;
    }

    public String build() {
        String tableName = table.getName();
        String columnsClause = buildColumnsClause();
        String valueClause = buildColumnsValueClause();
        return String.format(INSERT_QUERY_FORMAT, tableName, columnsClause, valueClause);
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

    private String buildColumnsValueClause() {
        StringBuilder valueClauseBuilder = new StringBuilder();

        valueClauseBuilder.append("null");

        List<Column> columns = table.getColumns();
        columns.forEach(column -> {
            String columnValueClause = buildColumnValueClause(column, instance);
            valueClauseBuilder
                    .append(',')
                    .append(columnValueClause);
        });

        return valueClauseBuilder.toString();
    }

    private String buildColumnValueClause(Column column, Object instance) {
        StringBuilder valueClauseBuilder = new StringBuilder();

        Object value = column.getValue(instance);
        if (column.isType(String.class)) {
            return valueClauseBuilder.append('\'')
                    .append(value)
                    .append('\'')
                    .toString();
        }

        valueClauseBuilder.append(value);
        return valueClauseBuilder.toString();
    }
}
