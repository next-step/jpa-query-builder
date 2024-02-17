package persistence.sql.ddl.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import persistence.sql.ddl.DataType;
import persistence.sql.ddl.QueryBuilder;

public class QueryBuilder2 extends QueryBuilder {
    protected String getTableNameFrom(final Class<?> clazz) {
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
            getColumnConstraintsFrom(field),
            getPrimaryKeyConstraintFrom(field)
        )
        .filter(s -> !s.isBlank())
        .collect(Collectors.joining(" "));
    }

    protected String getPrimaryKeyConstraintFrom(Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            return "";
        }

        // TODO: implementation specific to the database

        return "AUTO_INCREMENT";
    }

    protected String getColumnNameFrom(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }

        Column column = field.getAnnotation(Column.class);

        if (column.name().isEmpty()) {
            return field.getName();
        }

        return column.name();
    }

    protected String getColumnConstraintsFrom(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return "";
        }

        List<String> constraints = new ArrayList<>();

        Column column = field.getAnnotation(Column.class);

        if (column.unique()) {
            constraints.add("UNIQUE");
        }

        if (!column.nullable()) {
            constraints.add("NOT NULL");
        }

        return String.join(" ", constraints);
    }
}
