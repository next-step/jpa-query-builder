package persistence.util;

import persistence.exception.PersistenceException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtils {

    private ReflectionUtils() {}

    public static List<Object> getFieldValues(final Object object, final List<String> fieldNames) {
        return fieldNames
                .stream()
                .map(fieldName -> getFieldValue(object, fieldName))
                .collect(Collectors.toList());
    }

    public static Object getFieldValue(final Object object, final String fieldName) {
        try {
            final Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (final IllegalAccessException | NoSuchFieldException e) {
            throw new PersistenceException(e);
        }
    }
}
