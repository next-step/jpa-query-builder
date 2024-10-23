package persistence.sql.dml;

import persistence.sql.ddl.DatabaseDialect;
import persistence.sql.ddl.H2Dialect;
import persistence.sql.ddl.TableName;

public class DmlQueryBuilder {
    private static final String SELECT_ALL_TEMPLATE = "SELECT * FROM %s;";
    private static final String SELECT_BY_ID_TEMPLATE = "SELECT * FROM %s WHERE %s = %s;";
    private static final String INSERT_TEMPLATE =  "INSERT INTO %s (%s) VALUES (%s);";
    private static final String DELETE_TEMPLATE = "DELETE FROM %s WHERE %s = %s;";

    private final DatabaseDialect dialect;

    public DmlQueryBuilder(final H2Dialect h2Dialect) {
        this.dialect = h2Dialect;
    }

    public String delete(final Class<?> clazz, final Long id) {
        final String tableName = new TableName(clazz).value();
        final String idColumnName = new IdColumnName(clazz).getIdColumnName();
        return DELETE_TEMPLATE.formatted(
                tableName,
                idColumnName,
                whereClause(id)
        );
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
                whereClause(id)
        );
    }

    public String insert(final Class<?> clazz, final Object object) {
        final String tableName = new TableName(clazz).value();
        final String columns = new ColumnName(clazz).value();
        final String values = new InsertValues(clazz).value(object);

        return String.format(INSERT_TEMPLATE, tableName, columns, values);
    }

    private String whereClause(final Object value) {
        return switch (value) {
            case null -> "NULL";
            case final String s -> String.format("'%s'", s.replace("'", "''"));
            case final Number number -> value.toString();
            default -> String.format("'%s'", value);
        };
    }
}
