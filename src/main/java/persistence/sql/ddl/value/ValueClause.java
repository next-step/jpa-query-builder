package persistence.sql.ddl.value;

import java.lang.reflect.Field;

public class ValueClause {
    public static final String APOSTROPHE = "'";
    private final String value;
    public ValueClause(Field field, Object object) {
        try {
            field.setAccessible(true);
            this.value = getValue(field, object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("value 생성에 실패하였습니다.", e);
        }
    }

    private static String getValue(Field field, Object object) throws IllegalAccessException {
        if (String.class.isAssignableFrom(field.getType())) {
            return APOSTROPHE + field.get(object) + APOSTROPHE;
        }
        return String.valueOf(field.get(object));
    }

    public String getQuery() {
        return this.value;
    }
}
