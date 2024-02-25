package persistence.sql.domain;

import java.lang.reflect.Field;

import static persistence.sql.CommonConstant.COLON;

public class ColumnValue {

    private final Class<?> javaType;

    private final Object value;

    public ColumnValue(Field field, Object instance) {
        this.javaType = field.getType();
        if (instance == null) {
            this.value = null;
            return;
        }
        this.value = getObjectValue(field, instance);
    }

    private Object getObjectValue(Field field, Object instance) {
        Object objectValue;
        try {
            field.setAccessible(true);
            objectValue = field.get(instance);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        return objectValue;
    }

    public String getValue() {
        if (value == null) {
            return null;
        }
        if (javaType.equals(String.class)) {
            return COLON + value + COLON;
        }
        return String.valueOf(value);
    }

    public Class<?> getColumnObjectType() {
        return javaType;
    }
}
