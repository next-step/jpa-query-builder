package persistence.sql;

import persistence.sql.clause.Clause;
import persistence.sql.clause.ConditionalClause;
import persistence.sql.clause.WhereConditionalClause;
import persistence.sql.data.QueryType;
import persistence.sql.dml.MetadataLoader;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface QueryBuilder {
    String DELIMITER = ", ";

    QueryType queryType();

    boolean supported(QueryType queryType);

    String build(MetadataLoader<?> loader, Clause... clauses);

    default String getWhereClause(ConditionalClause... conditionalClauses) {
        return Arrays.stream(conditionalClauses)
                .filter(clause -> clause instanceof WhereConditionalClause)
                .map(ConditionalClause::clause)
                .collect(Collectors.joining(" and ")); // TODO 차후 or 조건도 지원할 수 있도록 수정 필요
    }
}
