package persistence.sql.ddl;

public class CreateQueryTranslator {
    private final ColumnTranslator columnTranslator;

    private final TableTranslator tableTranslator;

    public CreateQueryTranslator(ColumnTranslator columnTranslator,
        TableTranslator tableTranslator) {
        this.columnTranslator = columnTranslator;
        this.tableTranslator = tableTranslator;
    }

    public String getCreateTableQuery(final Class<?> entityClass) {
        return String.format(
            "CREATE TABLE %s (%s)",
            tableTranslator.getTableNameFrom(entityClass),
            columnTranslator.getColumnDefinitionsFrom(entityClass)
        );
    }
}
