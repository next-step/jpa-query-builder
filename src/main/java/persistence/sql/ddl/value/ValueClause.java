package persistence.sql.ddl.value;

import java.lang.reflect.Field;

public class ValueClause {
    public static final String APOSTROPHE = "'";
    private final String value;
    public ValueClause(Field field, Object instance) {
        try {
            field.setAccessible(true);
            this.value = getValueFromInstance(field, instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("value 생성에 실패하였습니다.", e);
        }
    }

    private static String getValueFromInstance(Field field, Object instance) throws IllegalAccessException {
        if (String.class.isAssignableFrom(field.getType()) && field.get(instance) != null) {
            return APOSTROPHE + field.get(instance) + APOSTROPHE;
        }
        return String.valueOf(field.get(instance));
    }

    private static String getValueFromColumnValue(Field field, Object instance) throws IllegalAccessException {
        if (String.class.isAssignableFrom(field.getType())) {
            return APOSTROPHE + instance + APOSTROPHE;
        }
        return String.valueOf(field.get(instance));
    }

    public String value() {
        return this.value;
    }
}
