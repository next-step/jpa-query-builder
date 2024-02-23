package persistence.sql.dml.domain;

import persistence.sql.ddl.domain.Column;

import java.lang.reflect.Field;

public class Value {

    private final Column column;
    private final String value;

    public Value(Column column, Field field, Object object) {
        this.column = column;
        try {
            field.setAccessible(true);
            this.value = convertValue(field.getType(), String.valueOf(field.get(object)));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Value(Column column, Class<?> valueType, Object value) {
        this.column = column;
        this.value = convertValue(valueType, String.valueOf(value));
    }

    public String convertValue(Class<?> type, String value) {
        if (type.equals(String.class)) {
            value = "'" + value + "'";
        }
        return value;
    }

    public Column getColumn() {
        return column;
    }

    public String getValue() {
        return value;
    }
}
