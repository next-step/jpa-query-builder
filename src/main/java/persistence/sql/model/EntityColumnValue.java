package persistence.sql.model;

import java.lang.reflect.Field;

public class EntityColumnValue {

    private final String value;

    public EntityColumnValue(Field field, Object object) {
        this.value = makeColumnValue(field, object);
    }

    private String makeColumnValue(Field field, Object object) {
        field.setAccessible(true);
        try {
            return String.valueOf(field.get(object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("해당 객체에 접근할 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
