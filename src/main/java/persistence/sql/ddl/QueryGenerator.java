package persistence.sql.ddl;

public class QueryGenerator {
    public String drop(final Class<?> clazz) {
        final TableName tableName = new TableName(clazz);
        return "DROP TABLE IF EXISTS %s CASCADE;".formatted(tableName.value(clazz));
    }

    public String create(final Class<?> clazz) {
        final TableName tableName = new TableName(clazz);
        final ColumnDefinitions columnDefinitions = new ColumnDefinitions(clazz);
        return getTableHeader(tableName.value(clazz)) +
               columnDefinitions.value(clazz) +
               getTableFooter();
    }

    private String getTableHeader(final String tableName) {
        return "CREATE TABLE " + tableName + " (\n";
    }

    private String getTableFooter() {
        return ");";
    }
}
