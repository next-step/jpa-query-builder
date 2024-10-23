package persistence.sql.dml;

import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertValues {
    private final Class<?> clazz;

    public InsertValues(final Class<?> clazz) {this.clazz = clazz;}

    String value(final Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> extractValue(field, object))
                .collect(Collectors.joining(", "));
    }

    private String extractValue(final Field field, final Object object) {
        try {
            field.setAccessible(true);
            final Object value = field.get(object);
            return whereClause(value);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + field.getName(), e);
        }
    }

    private String whereClause(final Object value) {
        return switch (value) {
            case null -> "NULL";
            case final String s -> String.format("'%s'", s.replace("'", "''"));
            case final Number number -> value.toString();
            default -> String.format("'%s'", value);
        };
    }
}
