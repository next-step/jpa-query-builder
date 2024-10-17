package persistence.sql.dml.impl;

import persistence.sql.QueryBuilder;
import persistence.sql.clause.Clause;
import persistence.sql.data.ClauseType;
import persistence.sql.data.QueryType;
import persistence.sql.dml.MetadataLoader;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateQueryBuilder implements QueryBuilder {
    @Override
    public QueryType queryType() {
        return QueryType.UPDATE;
    }

    @Override
    public boolean supported(QueryType queryType) {
        return QueryType.UPDATE.equals(queryType);
    }

    @Override
    public String build(MetadataLoader<?> loader, Clause... clauses) {
        String tableName = loader.getTableName();
        List<Clause> setClauses = Clause.filterByClauseType(clauses, ClauseType.SET);
        List<Clause> whereClauses = Clause.filterByClauseType(clauses, ClauseType.WHERE);

        StringBuilder query = new StringBuilder("UPDATE %s SET %s".formatted(tableName, getSetClause(setClauses)));

        if (!whereClauses.isEmpty()) {
            query.append(" WHERE ").append(getWhereClause(whereClauses));
        }

        return query.toString();
    }

    private String getSetClause(List<Clause> clauses) {
        if (clauses.isEmpty()) {
            throw new IllegalArgumentException("Set clause is required");
        }
        return clauses.stream()
                .map(Clause::clause)
                .collect(Collectors.joining(DELIMITER));
    }
}
