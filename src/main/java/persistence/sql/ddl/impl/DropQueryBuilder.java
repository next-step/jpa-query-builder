package persistence.sql.ddl.impl;

import persistence.sql.clause.Clause;
import persistence.sql.QueryBuilder;
import persistence.sql.common.util.NameConverter;
import persistence.sql.data.QueryType;
import persistence.sql.dml.MetadataLoader;

public class DropQueryBuilder implements QueryBuilder {
    private final NameConverter nameConverter;

    public DropQueryBuilder(NameConverter nameConverter) {
        this.nameConverter = nameConverter;
    }

    @Override
    public QueryType queryType() {
        return QueryType.DROP;
    }

    @Override
    public boolean supported(QueryType queryType) {
        return QueryType.DROP.equals(queryType);
    }

    @Override
    public String build(MetadataLoader<?> loader, Clause... clauses) {
        String tableName = loader.getTableName();

        return "DROP TABLE " + nameConverter.convert(tableName);
    }
}
