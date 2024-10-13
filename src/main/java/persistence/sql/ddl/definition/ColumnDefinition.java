package persistence.sql.ddl.definition;

import jakarta.persistence.Column;
import persistence.sql.ddl.query.CreateQueryBuilder;

import java.lang.reflect.Field;

public class ColumnDefinition {
    private final String name;
    private final String type;
    private final boolean nullable;

    public ColumnDefinition(Field field) {
        this.name = determineColumnName(field);
        this.type = determineColumnType(field);
        this.nullable = determineNullable(field);
    }

    private static boolean determineNullable(Field field) {
        final boolean hasColumnAnnotation = field.isAnnotationPresent(Column.class);
        return hasColumnAnnotation && field.getAnnotation(Column.class).nullable();
    }

    private static String determineColumnType(Field field) {
        final String entityFieldType = field.getType().getSimpleName();
        return CreateQueryBuilder.SQLTypeTranslator.translate(entityFieldType);
    }

    private static String determineColumnName(Field field) {
        final String columnName = field.getName();

        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }

        return columnName;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public boolean shouldNotNull() {
        return !nullable;
    }

}
