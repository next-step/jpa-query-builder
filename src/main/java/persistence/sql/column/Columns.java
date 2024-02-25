package persistence.sql.column;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Columns {
    private static final String COMMA = ", ";

    private final List<GeneralColumn> values;

    public Columns(Field[] fields, Dialect dialect) {
        this.values = Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .map(field -> new GeneralColumn(field, dialect))
                .collect(Collectors.toList());
    }

    public String getColumnsDefinition() {
        return this.values
                .stream()
                .map(Column::getDefinition)
                .collect(Collectors.joining(COMMA));
    }

    public String getColumnNames() {
        return this.values
                .stream()
                .map(Column::getName)
                .collect(Collectors.joining(COMMA));
    }

    public List<GeneralColumn> getValues() {
        return values;
    }
}
