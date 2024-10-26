package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateValues {
    private final Class<?> clazz;

    public UpdateValues(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public Map<String, Object> value(final Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(this::isUpdatableField)
                .collect(Collectors.toMap(
                        this::getColumnName,
                        field -> extractValue(field, object)
                ));
    }

    private boolean isUpdatableField(final Field field) {
        return !field.isAnnotationPresent(Transient.class) &&
               !field.isAnnotationPresent(Id.class);
    }

    private String getColumnName(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return column != null && !column.name().isEmpty()
                ? column.name()
                : field.getName();
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
