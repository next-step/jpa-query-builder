package persistence.sql.ddl.impl;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import persistence.sql.ddl.DataType;
import persistence.sql.ddl.QueryBuilder;

public class QueryBuilder1 extends QueryBuilder {
    protected String getTableNameFrom(final Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            if (!table.name().isEmpty()) {
                return table.name();
            }
        }

        return clazz.getSimpleName();
    }

    protected String getTableColumnDefinitionFrom(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1))
            .map(this::getColumnDefinitionStatementFrom)
            .collect(Collectors.joining(", "));
    }

    protected String getColumnDefinitionStatementFrom(Field field) {
        return Stream.of(
            getColumnNameFrom(field),
            DataType.of(field),
            getPrimaryKeyConstraintFrom(field)
        )
        .filter(s -> !s.isBlank())
        .collect(Collectors.joining(" "));
    }

    protected String getPrimaryKeyConstraintFrom(Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            return "";
        }

        return "AUTO_INCREMENT";
    }

    protected String getColumnNameFrom(Field field) {
        return field.getName();
    }

}
