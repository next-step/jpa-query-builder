package persistence.sql.dml.impl;

import persistence.sql.clause.Clause;
import persistence.sql.clause.ConditionalClause;
import persistence.sql.QueryBuilder;
import persistence.sql.clause.SetValueClause;
import persistence.sql.data.QueryType;
import persistence.sql.dml.MetadataLoader;

import java.util.Arrays;
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
        String setClause = getSetClause(clauses);
        String whereClause = getWhereClause(clauses);
        StringBuilder query = new StringBuilder("UPDATE %s SET %s".formatted(tableName, setClause));

        if (whereClause != null && !whereClause.isBlank()) {
            query.append(" WHERE ").append(whereClause);
        }

        return query.toString();
    }

    private String getWhereClause(Clause[] clauses) {
        ConditionalClause[] conditionalClauses = Arrays.stream(clauses)
                .filter(clause -> clause instanceof ConditionalClause)
                .map(clause -> (ConditionalClause) clause)
                .toArray(ConditionalClause[]::new);

        return getWhereClause(conditionalClauses);
    }

    private String getSetClause(Clause... clauses) {
        List<SetValueClause> setConditionalClauses = Arrays.stream(clauses)
                .filter(clause -> clause instanceof SetValueClause)
                .map(clause -> (SetValueClause) clause)
                .toList();

        if (setConditionalClauses.isEmpty()) {
            throw new IllegalArgumentException("Set clause is required");
        }
        return setConditionalClauses.stream()
                .map(SetValueClause::clause)
                .collect(Collectors.joining(DELIMITER));
    }
}
