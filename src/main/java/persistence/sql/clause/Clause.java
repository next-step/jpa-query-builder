package persistence.sql.clause;

import java.lang.reflect.Field;

public interface Clause {
    String column();
    String value();
    String clause();

    static String toColumnValue(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }
        return value.toString();
    }

    static Object extractValue(Field field, Object value) {
        try {
            field.setAccessible(true);
            return field.get(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
