package persistence.sql.dml.impl;

import persistence.sql.QueryBuilder;
import persistence.sql.clause.Clause;
import persistence.sql.common.util.NameConverter;
import persistence.sql.data.ClauseType;
import persistence.sql.data.QueryType;
import persistence.sql.dml.MetadataLoader;

import java.util.List;

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
    public String build(MetadataLoader<?> loader, Clause... clauses) {
        String columns = String.join(DELIMITER, loader.getColumnNameAll(nameConverter));
        String tableName = loader.getTableName();

        StringBuilder query = new StringBuilder("SELECT %s FROM %s".formatted(columns, tableName));

        List<Clause> conditionalClauses = Clause.filterByClauseType(clauses, ClauseType.WHERE);

        if (!conditionalClauses.isEmpty()) {
            query.append(" WHERE ");
            query.append(getWhereClause(conditionalClauses));
        }

        return query.toString();
    }
}