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
        if (isStringField(field, instance)) {
            return APOSTROPHE + field.get(instance) + APOSTROPHE;
        }
        return String.valueOf(field.get(instance));
    }

    private static boolean isStringField(Field field, Object instance) throws IllegalAccessException {
        return String.class.isAssignableFrom(field.getType()) && field.get(instance) != null;
    }

    public String value() {
        return this.value;
    }
}
