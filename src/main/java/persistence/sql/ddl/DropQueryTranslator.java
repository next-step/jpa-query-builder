package persistence.sql.ddl;

public class DropQueryTranslator {
    private final TableTranslator tableTranslator;

    public DropQueryTranslator(TableTranslator tableTranslator) {
        this.tableTranslator = tableTranslator;
    }

    public String getDropTableQuery(Class<?> entityClass) {
        return String.format(
            "DROP TABLE %s",
            tableTranslator.getTableNameFrom(entityClass)
        );
    }
}
