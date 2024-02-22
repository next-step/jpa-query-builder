package persistence.sql.domain;

import java.lang.reflect.Field;

public class ColumnValue {

    private final Class<?> javaType;

    private final Object value;

    public ColumnValue(Field field, Object instance) {
        javaType = field.getType();
        if (instance == null){
            value = null;
            return;
        }
        Object objectValue;
        try {
            field.setAccessible(true);
            objectValue = field.get(instance);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        this.value = objectValue;
    }

    public ColumnValue(Class<?> javaType, Object value) {
        this.javaType = javaType;
        this.value = value;
    }

    public String getValue() {
        if (value == null){
            return null;
        }
        if (javaType.equals(String.class)){
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }

    public Class<?> getJavaType() {
        return javaType;
    }
}
