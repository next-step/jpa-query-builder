package hibernate;

import hibernate.entity.EntityClass;
import hibernate.entity.EntityColumn;
import hibernate.entity.EntityColumnFactory;
import hibernate.entity.strategy.ColumnOptionGenerateStrategy;
import hibernate.entity.strategy.IdIdentityOptionGenerateStrategy;
import hibernate.entity.strategy.NotNullOptionGenerateStrategy;
import hibernate.entity.strategy.PrimaryKetOptionGenerateStrategy;

import java.lang.reflect.Field;
import java.util.Arrays;
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
    public String generateCreateQuery(final Class<?> clazz) {
        EntityClass entity = new EntityClass(clazz);
        String columns = fieldsToQueryColumn(clazz.getDeclaredFields());
        return String.format(CREATE_TABLE_QUERY, entity.tableName(), columns);
    }

    private String fieldsToQueryColumn(final Field[] fields) {
        return Arrays.stream(fields)
                .filter(EntityColumnFactory::isAvailableCreateEntityColumn)
                .map(EntityColumnFactory::create)
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
