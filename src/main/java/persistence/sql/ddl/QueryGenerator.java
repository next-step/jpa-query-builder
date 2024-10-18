package persistence.sql.ddl;

public class QueryGenerator {
    private final DatabaseDialect dialect;

    public QueryGenerator(final DatabaseDialect dialect) {
        this.dialect = dialect;
    }

    public String drop(final Class<?> clazz) {
        return QueryTemplate.DROP_TABLE.format(tableName(clazz));
    }

    public String create(final Class<?> clazz) {
        return QueryTemplate.CREATE_TABLE.format(tableName(clazz), columnDefinitions(clazz));
    }

    private String tableName(final Class<?> clazz) {
        final TableName tableName = new TableName(clazz);
        return tableName.value();
    }

    private String columnDefinitions(final Class<?> clazz) {
        final ColumnDefinitionFactory columnDefinitionFactory = new ColumnDefinitionFactory(clazz, dialect);
        final ColumnDefinitions columnDefinitions = new ColumnDefinitions(columnDefinitionFactory.create());
        return columnDefinitions.generate();
    }
}
