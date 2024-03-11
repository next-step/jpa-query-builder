package persistence.sql.ddl;

public class DropQueryTranslator {
    private final TableQueryBuilder tableQueryBuilder;

    public DropQueryTranslator(TableQueryBuilder tableQueryBuilder) {
        this.tableQueryBuilder = tableQueryBuilder;
    }

    public String getDropTableQuery(Class<?> entityClass) {
        return String.format(
            "DROP TABLE %s",
            tableQueryBuilder.getTableNameFrom(entityClass)
        );
    }
}
