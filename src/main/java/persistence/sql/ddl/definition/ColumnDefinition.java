package persistence.sql.ddl.definition;

import jakarta.persistence.Column;
import persistence.sql.ddl.query.CreateQueryBuilder.SQLTypeTranslator;

import java.lang.reflect.Field;

public class ColumnDefinition {
    private final String name;
    private final String type;
    private final boolean nullable;
    private static final int DEFAULT_LENGTH = 255;

    public ColumnDefinition(Field field) {
        this.name = determineColumnName(field);
        this.type = determineColumnType(field);
        this.nullable = determineColumnNullable(field);
    }

    private static boolean determineColumnNullable(Field field) {
        final boolean hasColumnAnnotation = field.isAnnotationPresent(Column.class);
        if (!hasColumnAnnotation) {
            return true;
        }
        return field.getAnnotation(Column.class).nullable();
    }

    private static String determineColumnType(Field field) {
        final String entityFieldType = field.getType().getSimpleName();
        final int length = determineColumnLength(field);
        return SQLTypeTranslator.translate(entityFieldType, length);
    }

    private static int determineColumnLength(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            return column.length();
        }
        return DEFAULT_LENGTH;
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
