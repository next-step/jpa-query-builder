package persistence.sql.ddl.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.common.StringConstants;

public class DefaultQueryBuilder extends QueryBuilder {

    private static final String SCHEMA_TABLE_DELIMITER = ".";
    private static final String COLUMN_DEFINITION_DELIMITER = ", ";
    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";

    public String getTableNameFrom(Class<?> clazz) {
        return Stream.of(
            getSchemaNameFrom(clazz),
            getOnlyTableNameFrom(clazz)
        )
        .filter(s -> !s.isBlank())
        .collect(Collectors.joining(SCHEMA_TABLE_DELIMITER));
    }

    public String getTableColumnDefinitionFrom(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1))
            .map(this::getColumnDefinitionFrom)
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    public String getPrimaryKeyConstraintFrom(Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            return StringConstants.EMPTY_STRING;
        }

        return AUTO_INCREMENT;
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

    protected String getOnlyTableNameFrom(final Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            if (!table.name().isEmpty()) {
                return table.name();
            }
        }

        return clazz.getSimpleName();
    }

    protected String getSchemaNameFrom(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            if (!table.schema().isEmpty()) {
                return table.schema();
            }
        }

        return StringConstants.EMPTY_STRING;
    }
}
