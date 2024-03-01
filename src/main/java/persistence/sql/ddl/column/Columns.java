package persistence.sql.ddl.column;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Columns {

    private final List<EntityColumn> columns;

    private Columns(List<EntityColumn> columns) {
        this.columns = columns;

        checkHasIdAnnotation(columns);
    }

    public static Columns from(Class<?> entity) {
        List<EntityColumn> columns = Arrays.stream(entity.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(ColumnConvertor::convert)
                .collect(Collectors.toList());

        return new Columns(columns);
    }

    private void checkHasIdAnnotation(List<EntityColumn> columns) {
        boolean hasId = columns.stream()
                .anyMatch(EntityColumn::hasId);

        if (!hasId) {
            throw new IllegalArgumentException("table should have primary key");
        }
    }

    public String getColumnsDefinition() {
        return columns.stream()
                .map(EntityColumn::defineColumn)
                .collect(Collectors.joining(", "));
    }

    public String getColumnsClause() {
        return columns.stream()
                .filter(column -> !column.hasId())
                .map(EntityColumn::getName)
                .collect(Collectors.joining(", "));
    }

    public List<EntityColumn> getColumns() {
        return columns;
    }
}
