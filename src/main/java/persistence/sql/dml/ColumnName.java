package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ColumnName {
    private final Class<?> clazz;

    public ColumnName(final Class<?> clazz) {this.clazz = clazz;}

    String value() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::extractColumnName)
                .collect(Collectors.joining(", "));
    }

    private String extractColumnName(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return (column != null && !column.name().isEmpty())
                ? column.name()
                : field.getName().toLowerCase();
    }
}
