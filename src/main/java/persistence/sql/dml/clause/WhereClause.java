package persistence.sql.dml.clause;

import persistence.sql.dialect.Dialect;

import java.util.List;
import java.util.stream.Collectors;

public class WhereClause implements Clause {
    private final List<Clause> equalClauses;

    public WhereClause(List<Clause> equalClauses) {
        this.equalClauses = equalClauses;
    }

    public WhereClause(Clause equalClause) {
        this.equalClauses = List.of(equalClause);
    }

    @Override
    public String toSql(Dialect dialect) {
        String equalClausesSql = equalClauses
                .stream().map(equalClause -> equalClause.toSql(dialect))
                .collect(Collectors.joining(" AND "));

        if (equalClausesSql.isEmpty()) {
            return "";
        }
        return "(" + equalClausesSql + ")";
    }
}
