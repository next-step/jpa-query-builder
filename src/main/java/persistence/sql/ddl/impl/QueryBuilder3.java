package persistence.sql.ddl.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import persistence.sql.ddl.QueryBuilder;

public class QueryBuilder3 extends QueryBuilder {
    public String getTableNameFrom(final Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            if (!table.name().isEmpty()) {
                return table.name();
            }
        }

        return clazz.getSimpleName();
    }

    public String getTableColumnDefinitionFrom(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1))
            .map(this::getColumnDefinitionFrom)
            .collect(Collectors.joining(", "));
    }

    public String getPrimaryKeyConstraintFrom(Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            return "";
        }

        return "AUTO_INCREMENT";
    }

    public String getColumnNameFrom(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }

        Column column = field.getAnnotation(Column.class);

        if (column.name().isEmpty()) {
            return field.getName();
        }

        return column.name();
    }
}
