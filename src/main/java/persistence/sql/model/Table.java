package persistence.sql.model;

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
        return new Table(name.toUpperCase(), columns);
    }

    public String getName() {
        return this.name;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public List<String> getColumnDefinitions() {
        return this.columns.stream()
            .map(Column::getDefinition)
            .collect(Collectors.toList());
    }
}
