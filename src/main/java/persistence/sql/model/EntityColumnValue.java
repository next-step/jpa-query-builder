package persistence.sql.model;

import java.lang.reflect.Field;

public class EntityColumnValue {

    private final Field field;
    private final Object object;

    public EntityColumnValue(Field field, Object object) {
        this.field = field;
        this.object = object;
    }

    public String getValue() {
        field.setAccessible(true);
        try {
            Object fieldObject = field.get(object);
            if (field.get(object) instanceof String) {
                return String.format("'%s'", String.valueOf(fieldObject));
            }
            return String.valueOf(fieldObject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("해당 객체에 접근할 수 없습니다.");
        }
    }
}
