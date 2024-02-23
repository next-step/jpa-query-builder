package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static common.StringConstants.COMMA;

public abstract class QueryBuilder {

    protected String generateTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return clazz.getSimpleName().toUpperCase();
        }

        Table table = clazz.getAnnotation(Table.class);
        if (table.name().isEmpty()) {
            return clazz.getSimpleName().toUpperCase();
        }
        return table.name().toUpperCase();
    }

    protected String generateColumns(Field[] fields) {
        return Arrays.stream(fields)
                .filter(this::isNotTransientAnnotationPresent)
                .map(this::generateColumn)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(COMMA));
    }

    protected abstract String generateColumn(Field field);

    protected String generateColumnName(Field field) {
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

}
