package persistence.util;

import persistence.exception.PersistenceException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtils {

    private ReflectionUtils() {
    }

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

    public static <T> T createInstance(final Class<T> clazz) {
        try {
            final Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        } catch (final Exception e) {
            throw new PersistenceException(clazz.getName() + " 인스턴스 생성에 실패했습니다.", e);
        }
    }

    public static void injectField(final Object object, final String fieldName, final Object fieldValue) {
        final Class<?> clazz = object.getClass();
        try {
            final Field declaredField = clazz.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(object, fieldValue);
        } catch (final Exception e) {
            throw new PersistenceException(clazz.getName() + " 필드 주입에 실패했습니다.", e);
        }
    }
}
