package persistence.sql.ddl;

import java.util.List;
import java.util.stream.Collectors;

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
        final List<ColumnDefinition> columnDefinitions = columnDefinitionFactory.create(clazz);
        return columnDefinitions.stream()
                .map(this::getColumnDefinition)
                .collect(Collectors.joining(",\n"));
    }

    private String getColumnDefinition(final ColumnDefinition definition) {
        return QueryTemplate.COLUMN_DEFINITION.format(
                definition.name(),
                definition.type(),
                definition.identity(),
                definition.nullable(),
                definition.primaryKey());
    }
}
