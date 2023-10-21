package hibernate.ddl;

import hibernate.ddl.strategy.ColumnOptionGenerateStrategy;
import hibernate.ddl.strategy.IdIdentityOptionGenerateStrategy;
import hibernate.ddl.strategy.NotNullOptionGenerateStrategy;
import hibernate.ddl.strategy.PrimaryKetOptionGenerateStrategy;
import hibernate.entity.EntityClass;
import hibernate.entity.column.EntityColumn;

import java.util.List;
import java.util.stream.Collectors;

public class CreateQueryBuilder {

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
