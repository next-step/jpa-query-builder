package persistence.sql.dml.model;

import persistence.sql.ColumnUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Value {

    private static final String SEPARATOR = ", ";

    private final Object entity;

    public Value(Object entity) {
        this.entity = entity;
    }

    public String get() {
        final Class<?> clz = entity.getClass();

        return Arrays.stream(clz.getDeclaredFields())
                .filter(ColumnUtils::includeColumn)
                .map(field -> value(field, entity))
                .collect(Collectors.joining(SEPARATOR));
    }

    public String clause(Field field, Object entity) {
        final String value = value(field, entity);

        if (value.equals("null")) {
            return null;
        }

        return ColumnUtils.name(field) + " = " + value;
    }

    private String value(Field field, Object entity) {
        Object value;

        field.setAccessible(true);
        try {
            value = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return mappingValue(value);
    }

    private String mappingValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }

}
