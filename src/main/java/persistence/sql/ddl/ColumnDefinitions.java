package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ColumnDefinitions {
    private static final String INDENTATION = "    ";
    private static final int VARCHAR_DEFAULT_LENGTH = 255;

    private final Class<?> clazz;

    public ColumnDefinitions(final Class<?> clazz) {
        this.clazz = clazz;
    }

    String getColumnDefinitions(final Class<?> entityClass) {
        final Field[] fields = entityClass.getDeclaredFields();
        return Stream.of(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::getColumnDefinition)
                .collect(Collectors.joining(",\n"));
    }

    private String getColumnDefinition(final Field field) {
        return "%s%s %s%s%s%s".formatted(
                INDENTATION,
                columnName(field),
                columnType(field),
                identityClause(field),
                nullableClause(field),
                primaryKeyClause(field));
    }

    private String primaryKeyClause(final Field field) {
        return isIdField(field) ? " PRIMARY KEY" : "";
    }

    private String nullableClause(final Field field) {
        return isNullable(field) ? "" : " NOT NULL";
    }

    private String identityClause(final Field field) {
        return isIdentity(field) ? " AUTO_INCREMENT" : "";
    }

    private String columnName(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return (column != null && !column.name().isEmpty()) ? column.name() : field.getName().toLowerCase();
    }
    private boolean isIdentity(final Field field) {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        return generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY;
    }

    private boolean isNullable(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return column == null || column.nullable();
    }

    private boolean isIdField(final Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private String columnType(final Field field) {
        final Class<?> type = field.getType();
        if (type == Long.class) {
            return "BIGINT";
        } else if (type == String.class) {
            return "VARCHAR(" + VARCHAR_DEFAULT_LENGTH + ")";
        } else if (type == Integer.class) {
            return "INTEGER";
        } else {
            return "VARCHAR(" + VARCHAR_DEFAULT_LENGTH + ")";
        }
    }
}
