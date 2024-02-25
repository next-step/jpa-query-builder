package persistence.sql.ddl.table;

import persistence.sql.ddl.column.Column;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Table {

    private final TableName name;
    private final List<Column> columns;

    private Table(TableName name, List<Column> columns) {
        this.name = name;
        this.columns = columns;

        checkHasIdAnnotation(columns);
    }

    public static Table from(Class<?> entity) {
        TableName name = TableName.from(entity);

        List<Column> columns = Arrays.stream(entity.getDeclaredFields())
                .map(Column::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new Table(name, columns);
    }

    private void checkHasIdAnnotation(List<Column> columns) {
        boolean hasId = columns.stream()
                .anyMatch(Column::hasId);

        if (!hasId) {
            throw new IllegalArgumentException(String.format("%s table should have primary key", name.getName()));
        }
    }

    public String getName() {
        return name.getName();
    }

    public String getColumnsDefinition() {
        return columns.stream()
                .map(Column::defineColumn)
                .collect(Collectors.joining(", "));
    }
}
