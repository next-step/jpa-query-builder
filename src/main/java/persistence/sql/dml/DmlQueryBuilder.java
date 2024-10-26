package persistence.sql.dml;

import persistence.sql.ddl.TableName;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DmlQueryBuilder {
    private static final String SELECT_MAX_ID_TEMPLATE = "SELECT MAX(%s) FROM %s;";
    private static final String SELECT_ALL_TEMPLATE = "SELECT * FROM %s;";
    private static final String SELECT_BY_ID_TEMPLATE = "SELECT * FROM %s WHERE %s = %s;";
    private static final String INSERT_TEMPLATE =  "INSERT INTO %s (%s) VALUES (%s);";
    private static final String DELETE_TEMPLATE = "DELETE FROM %s WHERE %s = %s;";
    private static final String UPDATE_TEMPLATE = "UPDATE %s SET %s WHERE %s = %s;";

    public String update(final Class<?> clazz, final Object entity, final Long id) {
        final String tableName = new TableName(clazz).value();
        final String setClause = formatSetClause(clazz, entity);
        final String idColumnName = new IdColumnName(clazz).getIdColumnName();

        return UPDATE_TEMPLATE.formatted(
                tableName,
                setClause,
                idColumnName,
                formatSqlValue(id)
        );
    }

    private String formatSetClause(final Class<?> clazz, final Object entity) {
        final Map<String, Object> updateValues = new UpdateValues(clazz).value(entity);

        return updateValues.entrySet().stream()
                .map(entry -> String.format("%s = %s",
                        entry.getKey(),
                        formatSqlValue(entry.getValue())))
                .collect(Collectors.joining(", "));
    }

    public String delete(final Class<?> clazz, final Long id) {
        final String tableName = new TableName(clazz).value();
        final String idColumnName = new IdColumnName(clazz).getIdColumnName();
        return DELETE_TEMPLATE.formatted(
                tableName,
                idColumnName,
                formatSqlValue(id)
        );
    }

    public String selectMaxId(final Class<?> clazz) {
        final String tableName = new TableName(clazz).value();
        final String idColumnName = new IdColumnName(clazz).getIdColumnName();
        return SELECT_MAX_ID_TEMPLATE.formatted(idColumnName, tableName);
    }

    public String select(final Class<?> clazz) {
        final String tableName = new TableName(clazz).value();
        return SELECT_ALL_TEMPLATE.formatted(tableName);
    }

    public String select(final Class<?> clazz, final Long id) {
        final String tableName = new TableName(clazz).value();
        final String idColumnName = new IdColumnName(clazz).getIdColumnName();
        return SELECT_BY_ID_TEMPLATE.formatted(
                tableName,
                idColumnName,
                formatSqlValue(id)
        );
    }

    public String insert(final Class<?> clazz, final Object object) {
        final String tableName = new TableName(clazz).value();
        final String columns = formatColumns(clazz);
        final List<Object> value = new InsertValues(clazz).value(object);

        return String.format(INSERT_TEMPLATE, tableName, columns, formatSqlValues(value));
    }

    private String formatColumns(final Class<?> clazz) {
        final List<String> columns = new ColumnName(clazz).value();
        return columns.stream()
                .collect(Collectors.joining(", "));
    }

    private String formatSqlValues(final List<Object> value) {
        return value.stream()
                .map(this::formatSqlValue)
                .reduce((a, b) -> a + ", " + b)
                .orElseThrow();
    }

    private String formatSqlValue(final Object value) {
        return switch (value) {
            case null -> "NULL";
            case final String s -> String.format("'%s'", s.replace("'", "''"));
            case final Number number -> value.toString();
            default -> String.format("'%s'", value);
        };
    }
}
