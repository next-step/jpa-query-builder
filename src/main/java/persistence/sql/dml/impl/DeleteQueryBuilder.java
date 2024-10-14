package persistence.sql.dml.impl;

import persistence.sql.common.util.NameConverter;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.QueryBuilder;
import persistence.sql.data.QueryType;

public class DeleteQueryBuilder implements QueryBuilder {
    private final NameConverter nameConverter;

    public DeleteQueryBuilder(NameConverter nameConverter) {
        this.nameConverter = nameConverter;
    }

    @Override
    public QueryType queryType() {
        return QueryType.DELETE;
    }

    @Override
    public boolean supported(QueryType queryType) {
        return QueryType.DELETE.equals(queryType);
    }

    @Override
    public String build(MetadataLoader<?> loader, Object value) {
        String tableName = loader.getTableName();
        String whereClause = getWhereIdClause(loader, value);

        return "DELETE FROM %s WHERE %s".formatted(nameConverter.convert(tableName), whereClause);
    }
}
