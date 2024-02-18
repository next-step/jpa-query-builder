package persistence.study;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectionUtil {

    public static Object runMethod(final Object instance, final Method method) {
        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object createNewInstance(final Class<Car> carClass) throws Exception {
        return carClass.getDeclaredConstructor(String.class, int.class).newInstance("nextstep", 123);
    }

    public static void setField(final Field field, final Object carInstance, final Object value) {
        try {
            field.set(carInstance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
