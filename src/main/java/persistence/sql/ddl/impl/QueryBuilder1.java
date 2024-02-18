package persistence.sql.ddl.impl;

import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import persistence.sql.ddl.QueryBuilder;

public class QueryBuilder1 extends QueryBuilder {
    public String getTableNameFrom(final Class<?> clazz) {
        return clazz.getSimpleName();
    }

    public String getTableColumnDefinitionFrom(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
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
        return field.getName();
    }
}
