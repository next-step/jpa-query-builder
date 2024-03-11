package persistence.sql.ddl;

public class DropQueryBuilder {
    private final TableQueryBuilder tableQueryBuilder;

    public DropQueryBuilder(TableQueryBuilder tableQueryBuilder) {
        this.tableQueryBuilder = tableQueryBuilder;
    }

    public String getDropTableQuery(Class<?> entityClass) {
        return String.format(
            "DROP TABLE %s",
            tableQueryBuilder.getTableNameFrom(entityClass)
        );
    }
}
