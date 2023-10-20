package hibernate;

import hibernate.entity.EntityClass;
import hibernate.entity.column.EntityColumn;
import hibernate.strategy.ColumnOptionGenerateStrategy;
import hibernate.strategy.IdIdentityOptionGenerateStrategy;
import hibernate.strategy.NotNullOptionGenerateStrategy;
import hibernate.strategy.PrimaryKetOptionGenerateStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder implements QueryBuilder {

    private static final String CREATE_TABLE_QUERY = "create table %s (%s);";
    private static final String CREATE_COLUMN_QUERY = "%s %s";
    private static final String CREATE_COLUMN_OPTION_DELIMITER = " ";
    private static final String CREATE_COLUMN_QUERY_DELIMITER = ", ";

    private final List<ColumnOptionGenerateStrategy> strategies = List.of(
            new NotNullOptionGenerateStrategy(),
            new PrimaryKetOptionGenerateStrategy(),
            new IdIdentityOptionGenerateStrategy()
    );

    public CreateQueryBuilder() {
    }

    @Override
    public String generateQuery(final EntityClass entity) {
        String columns = parseColumnQueries(entity.getEntityColumns());
        return String.format(CREATE_TABLE_QUERY, entity.tableName(), columns);
    }

    private String parseColumnQueries(final List<EntityColumn> entityColumns) {
        return entityColumns.stream()
                .map(this::parseColumnQuery)
                .collect(Collectors.joining(CREATE_COLUMN_QUERY_DELIMITER));
    }

    private String parseColumnQuery(final EntityColumn entityColumn) {
        String query = String.format(CREATE_COLUMN_QUERY, entityColumn.getFieldName(), entityColumn.getColumnType().getH2ColumnType());
        return strategies.stream()
                .filter(strategy -> strategy.acceptable(entityColumn))
                .map(ColumnOptionGenerateStrategy::generateColumnOption)
                .collect(Collectors.joining(CREATE_COLUMN_OPTION_DELIMITER, query + CREATE_COLUMN_OPTION_DELIMITER, ""));
    }
}
