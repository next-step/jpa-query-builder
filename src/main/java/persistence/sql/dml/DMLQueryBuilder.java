package persistence.sql.dml;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class DMLQueryBuilder {

    String getTableName(Class<?> field) {
        if (field.isAnnotationPresent(Table.class)) {
            Table table = field.getAnnotation(Table.class);
            return !table.name().isEmpty() ? table.name() : field.getSimpleName();
        }
        return field.getSimpleName();
    }

    String columnsClause(Class<?> field) {
        return Arrays.stream(field.getDeclaredFields())
                .filter(c -> !c.isAnnotationPresent(Id.class) || !c.isAnnotationPresent(GeneratedValue.class))
                .filter(c -> !c.isAnnotationPresent(Transient.class)).map(this::getColumnName).collect(Collectors.joining(", "));
    }

    String valueClause(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class) || !field.isAnnotationPresent(GeneratedValue.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class)).map(field -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(entity);
                        if (value instanceof String) {
                            return "'" + value + "'";
                        }
                        return value != null ? value.toString() : "NULL";
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.joining(", "));
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
}
