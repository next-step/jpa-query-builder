package persistence;

import persistence.sql.QueryException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils {

    private ReflectionUtils() {}

    public static <T> T createInstance(final Class<T> clazz) {
        try {
            final Constructor<T> constructor = clazz.getDeclaredConstructor();
            final int parameterCount = constructor.getParameterCount();
            final Object[] parameters = new Object[parameterCount];

            return constructor.newInstance(parameters);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new QueryException("can't create " + clazz.getName() + " instance");
        } catch (NoSuchMethodException e) {
            throw new QueryException("not found " + clazz.getName() + " constructor");
        }
    }

    public static <T> void setFieldValue(final Field field, final T entity, final Object value) {
        try {
            field.setAccessible(true);
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            throw new QueryException("can't set field " + field.getName() + " at " + entity.getClass().getName());
        }
    }

}
