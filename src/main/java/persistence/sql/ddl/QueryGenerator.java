package persistence.sql.ddl;

public class QueryGenerator {
    private static final String DROP_TABLE_TEMPLATE = "DROP TABLE IF EXISTS %s CASCADE;";
    private static final String CREATE_TABLE_TEMPLATE = "CREATE TABLE %s (\n%s);";

    private final DatabaseDialect dialect;

    public QueryGenerator(final DatabaseDialect dialect) {
        this.dialect = dialect;}

    public String drop(final Class<?> clazz) {
        final TableName tableName = new TableName(clazz);
        return DROP_TABLE_TEMPLATE.formatted(tableName.value(clazz));
    }

    public String create(final Class<?> clazz) {
        final TableName tableName = new TableName(clazz);
        final ColumnDefinitions columnDefinitions = new ColumnDefinitions(clazz, dialect);
        return CREATE_TABLE_TEMPLATE.formatted(
                tableName.value(clazz),
                columnDefinitions.value(clazz));
    }
}
