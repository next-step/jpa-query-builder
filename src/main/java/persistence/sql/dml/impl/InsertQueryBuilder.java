package persistence.sql.dml.impl;

import persistence.sql.QueryBuilder;
import persistence.sql.clause.Clause;
import persistence.sql.clause.InsertColumnValueClause;
import persistence.sql.data.QueryType;
import persistence.sql.dml.MetadataLoader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder implements QueryBuilder {

    @Override
    public QueryType queryType() {
        return QueryType.INSERT;
    }

    @Override
    public boolean supported(QueryType queryType) {
        return QueryType.INSERT.equals(queryType);
    }

    @Override
    public String build(MetadataLoader<?> loader, Clause... clauses) {
        String tableName = loader.getTableName();
        if (clauses.length == 0) {
            throw new UnsupportedOperationException("Conditional clauses are not supported for insert query");
        }

        List<Clause> insertColumnValueClauses = Arrays.stream(clauses)
                .filter(clause -> clause instanceof InsertColumnValueClause).toList();

        if (insertColumnValueClauses.isEmpty()) {
            throw new UnsupportedOperationException("InsertColumnValueClause is required for insert query");
        }

        String columns = insertColumnValueClauses.getFirst().column();
        String values = Arrays.stream(clauses)
                .filter(clause -> clause instanceof InsertColumnValueClause)
                .map(Clause::clause)
                .collect(Collectors.joining(DELIMITER));

        return "INSERT INTO %s (%s) VALUES %s".formatted(tableName, columns, values);
    }
}
