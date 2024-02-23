package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class QueryBuilder {

    public static final String EMPTY_STRING = "";
    public static final String COMMA = ", ";

    public String generateTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return clazz.getSimpleName().toUpperCase();
        }

        Table table = clazz.getAnnotation(Table.class);
        if (table.name().isEmpty()) {
            return clazz.getSimpleName().toUpperCase();
        }
        return table.name().toUpperCase();
    }

    public String generateColumns(Field[] fields) {
        return Arrays.stream(fields)
                .filter(this::isNotTransientAnnotationPresent)
                .map(this::generateColumn)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(COMMA));
    }

    public abstract String generateColumn(Field field);

    public String generateColumnName(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName().toUpperCase();
        }

        Column column = field.getAnnotation(Column.class);
        if (column.name().isEmpty()) {
            return field.getName().toUpperCase();
        }
        return column.name().toUpperCase();
    }

    public boolean isNotTransientAnnotationPresent(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }


    public String convertValue(Class<?> type, String value) {
        if (type.equals(String.class)) {
            value = "'" + value + "'";
        }
        return value;
    }
}
