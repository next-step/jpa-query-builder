package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryGenerator {
    private static final String INDENTATION = "    ";
    private static final int VARCHAR_DEFAULT_LENGTH = 255;

    public String drop(final Class<?> clazz) {
        final String tableName = getTableName(clazz);
        return "DROP TABLE IF EXISTS %s CASCADE;".formatted(tableName);
    }

    public String create(final Class<?> clazz) {
        final String tableName = getTableName(clazz);
        return getTableHeader(tableName) +
               getColumnDefinitions(clazz) +
               getTableFooter();
    }

    private String getTableName(final Class<?> clazz) {
        final Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name().toUpperCase();
        }
        return clazz.getSimpleName().toUpperCase();
    }

    private String getTableHeader(final String tableName) {
        return "CREATE TABLE " + tableName + " (\n";
    }

    private String getColumnDefinitions(final Class<?> entityClass) {
        final Field[] fields = entityClass.getDeclaredFields();
        return Stream.of(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::getColumnDefinition)
                .collect(Collectors.joining(",\n"));
    }

    private String getColumnDefinition(final Field field) {
        final String columnName = getColumnName(field);
        final String columnType = getColumnType(field);
        final String identityClause = getIdentityClause(field);
        final String nullableClause = getNullableClause(field);
        final String primaryKeyClause = getPrimaryKeyClause(field);

        return INDENTATION +
               columnName +
               " " +
               columnType +
               identityClause +
               nullableClause +
               primaryKeyClause;
    }

    private String getPrimaryKeyClause(final Field field) {
        return isIdField(field) ? " PRIMARY KEY" : "";
    }

    private String getNullableClause(final Field field) {
        return isNullable(field) ? "" : " NOT NULL";
    }

    private String getIdentityClause(final Field field) {
        return isIdentity(field) ? " AUTO_INCREMENT" : "";
    }

    private String getColumnName(final Field field) {
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

    private String getColumnType(final Field field) {
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

    private String getTableFooter() {
        return ");";
    }
}
