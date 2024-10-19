package persistence.sql.dml;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class DMLQueryBuilder {
    private final Class<?> clazz;

    protected DMLQueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }
    String setCaluse(Object entity) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isPersistentField)
                .map(field -> getColumnName(field) + " = " + getFieldValue(entity, field))
                .collect(Collectors.joining(", "));
    }

    String idClause (Object entity) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(field -> getColumnName(field) + " = " + getFieldValue(entity, field))
                .collect(Collectors.joining(" AND "));
    }

    String getTableName() {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            return !table.name().isEmpty() ? table.name() : clazz.getSimpleName();
        }

        return clazz.getSimpleName();
    }

    String columnsClause() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isPersistentField)
                .map(this::getColumnName)
                .collect(Collectors.joining(", "));
    }

    String valueClause(Object entity) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isPersistentField)
                .map(field -> getFieldValue(entity, field))
                .collect(Collectors.joining(", "));
    }

    private String getFieldValue(Object entity, Field field) {
        try {
            field.setAccessible(true);
            Object value = field.get(entity);
            return formatValue(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return value != null ? value.toString() : "NULL";
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }
        return field.getName();
    }

    private boolean isPersistentField(Field field) {
        return !(field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class))
                && !field.isAnnotationPresent(Transient.class);
    }
}