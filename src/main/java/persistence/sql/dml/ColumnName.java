package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ColumnName {
    private final Class<?> clazz;

    public ColumnName(final Class<?> clazz) {this.clazz = clazz;}

    List<String> value() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::extractColumnName)
                .toList();
    }

    private String extractColumnName(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return (column != null && !column.name().isEmpty())
                ? column.name()
                : field.getName().toLowerCase();
    }
}
