package persistence.sql.dml.clause;

import persistence.sql.dialect.Dialect;

import java.util.List;
import java.util.stream.Collectors;

public class WhereClause implements Clause {
    private final List<EqualClause> equalClauses;

    public WhereClause(List<EqualClause> equalClauses) {
        this.equalClauses = equalClauses;
    }

    public WhereClause(EqualClause equalClause) {
        this.equalClauses = List.of(equalClause);
    }

    @Override
    public String toSql(Dialect dialect) {
        String equalOperatorsSQL = equalClauses
                .stream().map(equalClause -> equalClause.toSql(dialect))
                .collect(Collectors.joining(" AND "));
        return "(" + equalOperatorsSQL + ")";
    }
}
