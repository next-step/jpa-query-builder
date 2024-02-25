package persistence.sql.dml;

import persistence.sql.domain.Column;
import persistence.sql.domain.Table;

import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryBuilder {
    private static final String SELECT_QUERY_TEMPLATE = "SELECT %s FROM %s";
    private static final String WHERE_CLAUSE_TEMPLATE = " WHERE %s = %d";
    private static final String COLUMN_DELIMITER = ", ";

    public String build(Class<?> target, Object id) {
        Table table = Table.from(target);
        String columnsNames = getColumnsNames(table.getColumns());

        String selectQuery = String.format(SELECT_QUERY_TEMPLATE, columnsNames, table.getName());
        return selectQuery + whereClause(table, id);
    }

    private String getColumnsNames(List<Column> columns) {
        return columns.stream()
                .map(Column::getName)
                .collect(Collectors.joining(COLUMN_DELIMITER));
    }

    private String whereClause(Table table, Object id) {
        String name = table.getIdColumn().getName();
        return String.format(WHERE_CLAUSE_TEMPLATE, name, id);
    }
}
