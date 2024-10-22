package persistence.sql.dml;

import persistence.sql.ddl.DatabaseDialect;
import persistence.sql.ddl.H2Dialect;
import persistence.sql.ddl.TableName;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DmlQueryBuilder {
    private static final String SELECT_ALL_TEMPLATE = "SELECT * FROM %s;";
    private static final String SELECT_BY_ID_TEMPLATE = "SELECT * FROM %s WHERE %s = %s;";
    private static final String INSERT_TEMPLATE =  "INSERT INTO %s (%s) VALUES (%s);";

    private final DatabaseDialect dialect;

    public DmlQueryBuilder(final H2Dialect h2Dialect) {
        this.dialect = h2Dialect;
    }

    public String select(final Class<?> clazz) {
        final String tableName = new TableName(clazz).value();
        return SELECT_ALL_TEMPLATE.formatted(tableName);
    }

    public String select(final Class<?> clazz, final Long id) {
        final String tableName = new TableName(clazz).value();
        final String idColumnName = getIdColumnName(clazz);
        return SELECT_BY_ID_TEMPLATE.formatted(
                tableName,
                idColumnName,
                formatValue(id)
        );
    }

    private String getIdColumnName(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .map(this::extractColumnName)
                .orElseThrow(() -> new IllegalArgumentException("No @Id field found in " + clazz.getName()));
    }

    public String insert(final Class<?> clazz, final Object object) {
        final String tableName = new TableName(clazz).value();
        final String columns = columnsClause(object.getClass());
        final String values = valueClause(object);

        return String.format(INSERT_TEMPLATE, tableName, columns, values);
    }

    private String columnsClause(final Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !isGeneratedId(field))
                .map(this::extractColumnName)
                .collect(Collectors.joining(", "));
    }

    private String extractColumnName(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return (column != null && !column.name().isEmpty())
                ? column.name()
                : field.getName().toLowerCase();
    }

    private boolean isGeneratedId(final Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            return false;
        }
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        return generatedValue != null;
    }

    private String valueClause(final Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !isGeneratedId(field))
                .map(field -> extractValue(field, object))
                .collect(Collectors.joining(", "));
    }

    private String extractValue(final Field field, final Object object) {
        try {
            field.setAccessible(true);
            final Object value = field.get(object);
            return formatValue(value);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + field.getName(), e);
        }
    }

    private String formatValue(final Object value) {
        return switch (value) {
            case null -> "NULL";
            case final String s -> String.format("'%s'", s.replace("'", "''"));
            case final Number number -> value.toString();
            default -> String.format("'%s'", value);
        };
    }
}
