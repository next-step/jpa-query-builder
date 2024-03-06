package persistence.sql.ddl.value;

import java.lang.reflect.Field;

public class ValueClause {
    public static final String APOSTROPHE = "'";
    private final String value;
    public ValueClause(Field field, Object entity) {
        try {
            field.setAccessible(true);
            this.value = getValueFromInstance(field, entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("value 생성에 실패하였습니다.", e);
        }
    }

    private static String getValueFromInstance(Field field, Object entity) throws IllegalAccessException {
        if (isStringField(field, entity)) {
            return APOSTROPHE + field.get(entity) + APOSTROPHE;
        }
        return String.valueOf(field.get(entity));
    }

    private static boolean isStringField(Field field, Object entity) throws IllegalAccessException {
        return String.class.isAssignableFrom(field.getType()) && field.get(entity) != null;
    }

    public String value() {
        return this.value;
    }
}
