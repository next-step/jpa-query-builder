package persistence.sql.dml;

import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class InsertValues {
    private final Class<?> clazz;

    public InsertValues(final Class<?> clazz) {this.clazz = clazz;}

    List<Object> value(final Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> extractValue(field, object))
                .toList();
    }

    private Object extractValue(final Field field, final Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + field.getName(), e);
        }
    }
}
