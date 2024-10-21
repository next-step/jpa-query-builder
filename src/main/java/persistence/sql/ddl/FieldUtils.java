package persistence.sql.ddl;

import java.lang.reflect.Field;

public class FieldUtils {
    public static void setValue(Field field, Object object, Object value) {
        try {
            field.setAccessible(true);

            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }

    public static Object getValue(Field field, Object object) {
        try {
            field.setAccessible(true);

            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }
}
