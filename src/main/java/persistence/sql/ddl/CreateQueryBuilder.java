package persistence.sql.ddl;

import persistence.sql.ddl.strategy.AdditionalColumQueryStrategy;
import persistence.sql.ddl.strategy.AutoIncrementColumnStrategy;
import persistence.sql.ddl.strategy.NullableFalseColumnStrategy;
import persistence.sql.ddl.strategy.PrimaryKeyColumnStrategy;
import persistence.sql.domain.Column;
import persistence.sql.domain.DataType;
import persistence.sql.domain.Dialect;
import persistence.sql.domain.Table;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder {
    private static final String CREATE_QUERY_TEMPLATE = "CREATE TABLE %s (%s)";
    private static final String COLUMN_QUERIES_DELIMITER = ", ";
    private static final String SPACE = " ";
    private static final List<AdditionalColumQueryStrategy> STRATEGIES = List.of(
            new AutoIncrementColumnStrategy(),
            new NullableFalseColumnStrategy(),
            new PrimaryKeyColumnStrategy()
    );

    private final Table table;

    public CreateQueryBuilder(Class<?> target) {
        this.table = Table.of(target);
    }

    public String build() {
        String tableName = table.getName();
        List<Column> fieldColumns = table.getColumns();
        String columnQueries = makeColumnQueries(fieldColumns);
        return String.format(CREATE_QUERY_TEMPLATE, tableName, columnQueries);
    }

    private String makeColumnQueries(List<Column> fieldColumns) {
        return fieldColumns.stream()
                .map(this::makeColumnQuery)
                .collect(Collectors.joining(COLUMN_QUERIES_DELIMITER));
    }

    private String makeColumnQuery(Column column) {
        DataType columnType = column.getType();
        return column.getName() +
                SPACE +
                columnType.getDataTypeForDialect(Dialect.H2) +
                getColumnOptions(column.getField());
    }

    private String getColumnOptions(Field field) {
        return STRATEGIES.stream()
                .filter(strategy -> strategy.isRequired(field))
                .map(AdditionalColumQueryStrategy::fetchQueryPart)
                .collect(Collectors.joining());
    }
}
