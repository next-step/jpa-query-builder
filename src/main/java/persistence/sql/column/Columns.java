package persistence.sql.column;

import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Columns {
    private static final String COMMA = ", ";

    private final List<Column> values;

    private Columns(List<Column> values) {
        this.values = values;
    }

    public static Columns of(Field[] fields, Dialect dialect) {
        List<Column> columns = Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> ColumnFactory.create(field, dialect))
                .collect(Collectors.toList());
        return new Columns(columns);
    }

    public String getColumnsDefinition() {
        return this.values
                .stream()
                .map(Column::getDefinition)
                .collect(Collectors.joining(COMMA));
    }

    public String insertColumnsClause() {
        return this.values
                .stream()
                .filter(column -> !column.isPk())
                .map(Column::getColumnName)
                .collect(Collectors.joining(COMMA));
    }

    public Column getPkColumn() {
        return values.stream()
                .filter(Column::isPk)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No primary key found"));
    }

    public String getColumnNames() {
        return this.values
                .stream()
                .map(Column::getColumnName)
                .collect(Collectors.joining(COMMA));
    }
}
