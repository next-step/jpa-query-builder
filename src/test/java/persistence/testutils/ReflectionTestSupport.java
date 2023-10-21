package persistence.testutils;

import java.lang.reflect.Field;

public class ReflectionTestSupport {
    public static <T> void setFieldValue(T instance, String fieldName, Object value){
        Field field;
        try {
            field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
