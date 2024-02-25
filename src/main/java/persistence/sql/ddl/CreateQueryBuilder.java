package persistence.sql.ddl;

import persistence.sql.ddl.strategy.AdditionalColumQueryStrategy;
import persistence.sql.ddl.strategy.AutoIncrementColumnStrategy;
import persistence.sql.ddl.strategy.NullableFalseColumnStrategy;
import persistence.sql.ddl.strategy.PrimaryKeyColumnStrategy;
import persistence.sql.domain.Column;
import persistence.sql.domain.Table;
import persistence.sql.domain.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder {
    private static final String COLUMN_QUERIES_DELIMITER = ", ";
    private static final String SPACE = " ";
    private static final List<AdditionalColumQueryStrategy> STRATEGIES = List.of(
            new AutoIncrementColumnStrategy(),
            new NullableFalseColumnStrategy(),
            new PrimaryKeyColumnStrategy()
    );

    private final Dialect dialect;

    public CreateQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String build(Class<?> clazz) {
        Table table = Table.from(clazz);
        String tableName = table.getName();
        List<Column> fieldColumns = table.getColumns();
        String columnQueries = makeColumnQueries(fieldColumns);
        return String.format(dialect.getCreateQueryTemplate(), tableName, columnQueries);
    }

    private String makeColumnQueries(List<Column> fieldColumns) {
        return fieldColumns.stream()
                .map(this::makeColumnQuery)
                .collect(Collectors.joining(COLUMN_QUERIES_DELIMITER));
    }

    private String makeColumnQuery(Column column) {
        return column.getName() +
                SPACE +
                dialect.convertClassForDialect(column.getType()) +
                getColumnOptions(column.getField());
    }

    private String getColumnOptions(Field field) {
        return STRATEGIES.stream()
                .filter(strategy -> strategy.isRequired(field))
                .map(AdditionalColumQueryStrategy::fetchQueryPart)
                .collect(Collectors.joining());
    }
}
