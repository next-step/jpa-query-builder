package persistence.sql.dml.impl;

import persistence.sql.clause.Clause;
import persistence.sql.clause.ConditionalClause;
import persistence.sql.QueryBuilder;
import persistence.sql.common.util.NameConverter;
import persistence.sql.data.QueryType;
import persistence.sql.dml.MetadataLoader;

import java.util.Arrays;

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
    public String build(MetadataLoader<?> loader, Clause... clauses) {
        String tableName = loader.getTableName();
        StringBuilder query = new StringBuilder("DELETE FROM " + nameConverter.convert(tableName));

        if (clauses.length == 0) {
            return query.toString();
        }

        ConditionalClause[] conditionalClauses = Arrays.stream(clauses)
                .filter(clause -> clause instanceof ConditionalClause)
                .map(clause -> (ConditionalClause) clause)
                .toArray(ConditionalClause[]::new);

        if (conditionalClauses.length > 0) {
            query.append(" WHERE ");
            query.append(getWhereClause(conditionalClauses));
        }

        return query.toString();
    }
}
