package persistence.sql.model;

import persistence.sql.dialect.Dialect;

import java.util.List;
import java.util.stream.Collectors;

public class Table {

    private final String name;
    private final List<Column> columns;

    private Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    public static Table create(String name, List<Column> columns) {
        validatePrimaryKey(columns);
        return new Table(name.toUpperCase(), columns);
    }

    private static void validatePrimaryKey(List<Column> columns) {
        columns.stream()
            .filter(Column::isPrimary)
            .findAny()
            .orElseThrow(PrimaryKeyNotFoundException::new);
    }

    public String getName() {
        return this.name;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public List<String> getColumnDefinitions(Dialect dialect) {
        return this.columns.stream()
            .map(column -> column.getDefinition(dialect))
            .collect(Collectors.toList());
    }
}
