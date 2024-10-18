package persistence.sql.ddl;

import java.util.List;
import java.util.stream.Collectors;

public class QueryGenerator {
    private static final String DROP_TABLE_TEMPLATE = "DROP TABLE IF EXISTS %s CASCADE;";
    private static final String CREATE_TABLE_TEMPLATE = "CREATE TABLE %s (\n%s);";
    private static final String COLUMN_DEFINITION_TEMPLATE = "%s%s %s%s%s%s";
    private static final String INDENTATION = "    ";

    private final DatabaseDialect dialect;

    public QueryGenerator(final DatabaseDialect dialect) {
        this.dialect = dialect;}

    public String drop(final Class<?> clazz) {
        final TableName tableName = new TableName(clazz);
        return DROP_TABLE_TEMPLATE.formatted(tableName.value(clazz));
    }

    public String create(final Class<?> clazz) {
        return CREATE_TABLE_TEMPLATE.formatted(
                tableName(clazz),
                columnDefinitions(clazz));
    }

    private String tableName(final Class<?> clazz) {
        final TableName tableName = new TableName(clazz);
        return tableName.value(clazz);
    }

    private String columnDefinitions(final Class<?> clazz) {
        final ColumnDefinitionFactory columnDefinitionFactory = new ColumnDefinitionFactory(clazz, dialect);
        final List<ColumnDefinition> columnDefinitions = columnDefinitionFactory.create(clazz);
        return columnDefinitions.stream()
                .map(this::getColumnDefinition)
                .collect(Collectors.joining(",\n"));
    }

    private String getColumnDefinition(final ColumnDefinition definition) {
        return COLUMN_DEFINITION_TEMPLATE.formatted(
                INDENTATION,
                definition.name(),
                definition.type(),
                definition.identity(),
                definition.nullable(),
                definition.primaryKey());
    }
}
