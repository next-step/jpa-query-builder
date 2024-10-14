package persistence.sql.dml.impl;

import persistence.sql.common.util.NameConverter;
import persistence.sql.dml.MetadataLoader;
import persistence.sql.QueryBuilder;
import persistence.sql.data.QueryType;

public class SelectQueryBuilder implements QueryBuilder {
    private final NameConverter nameConverter;

    public SelectQueryBuilder(NameConverter nameConverter) {
        this.nameConverter = nameConverter;
    }

    @Override
    public QueryType queryType() {
        return QueryType.SELECT;
    }

    @Override
    public boolean supported(QueryType queryType) {
        return QueryType.SELECT.equals(queryType);
    }

    @Override
    public String build(MetadataLoader<?> loader, Object value) {
        String columns = String.join(DELIMITER, loader.getColumnNameAll(nameConverter));
        String tableName = loader.getTableName();

        if (value == null) {
            return "SELECT %s FROM %s".formatted(columns, tableName);
        }

        return "SELECT %s FROM %s WHERE %s".formatted(columns, tableName, getWhereIdClause(loader, value));
    }
}
