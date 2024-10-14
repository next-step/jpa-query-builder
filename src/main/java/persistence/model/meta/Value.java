package persistence.model.meta;

import java.lang.reflect.Field;

public class Value {
    private Object value = null;

    private Class<?> type;

    private Value(Object value, Class<?> type) {
        this.value = value;
        this.type = type;
    }

    private Value() {
    }

    public static Value create(Object entityObject, Field field) {
        field.setAccessible(true);
        try {
            Object value = field.get(entityObject);
            if (value == null) {
                return new Value();
            }
            Class<?> type = field.getType();
            return new Value(value, type);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getType() {
        return type;
    }

    public Boolean isNull() {
        return value == null;
    }
}
