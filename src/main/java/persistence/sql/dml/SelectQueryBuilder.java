package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SelectQueryBuilder {
    public String findAll(Class<?> clazz) {
        String tableName = getTableName(clazz);
        String tableColumns = getTableColumns(clazz);
        return String.format("select %s FROM %s", tableColumns, tableName);
    }

    private static String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation == null) {
            return clazz.getSimpleName().toLowerCase();
        }

        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return clazz.getSimpleName().toLowerCase();
    }

    private String getTableColumns(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::getTableColumn)
                .collect(Collectors.joining(", "));
    }

    private String getTableColumn(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation == null) {
            return field.getName();
        }
        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return field.getName();
    }
}
