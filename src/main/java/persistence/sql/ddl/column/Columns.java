package persistence.sql.ddl.column;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Columns {

    private final List<Column> columns;

    private Columns(List<Column> columns) {
        this.columns = columns;

        checkHasIdAnnotation(columns);
    }

    public static Columns from(Class<?> entity) {
        List<Column> columns = Arrays.stream(entity.getDeclaredFields())
                .map(Column::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new Columns(columns);
    }

    private void checkHasIdAnnotation(List<Column> columns) {
        boolean hasId = columns.stream()
                .anyMatch(Column::hasId);

        if (!hasId) {
            throw new IllegalArgumentException("table should have primary key");
        }
    }

    public String getColumnsDefinition() {
        return columns.stream()
                .map(Column::defineColumn)
                .collect(Collectors.joining(", "));
    }
}
