package persistence.sql.dml.converter;

import jakarta.persistence.Id;
import persistence.sql.ColumnUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class H2ColumnConverter implements ColumnConverter {

    private static final String SEPARATOR = ", ";

    @Override
    public String fields(Class<?> clz) {
        final Field[] fields = clz.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(ColumnUtils::excludeColumn)
                .map(ColumnUtils::name)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public String values(Class<?> clz, Object entity) {
        final Field[] fields = clz.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(ColumnUtils::excludeColumn)
                .map(field -> value(field, entity))
                .collect(Collectors.joining(SEPARATOR));
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

    @Override
    public String where(Class<?> clz, Object id) {
        final Field[] fields = clz.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(this::isIdField)
                .findAny()
                .map(Field::getName)
                .orElseThrow(() -> new IllegalArgumentException(id + " field not exist!"));
    }

    private boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }
}
