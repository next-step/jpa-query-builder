package persistence.sql.dml.querybuilder;

public class WhereClause {

    private static final String WHERE_SQL = " WHERE ";

    private final QueryBuilder builder;

    public WhereClause(QueryBuilder builder) {
        this.builder = builder;
    }

    public String getClause() {
        StringBuilder query = new StringBuilder();
        if (!builder.getConditions().isEmpty()) {
            query.append(WHERE_SQL);
            String conditionsPart = String.join(" ", builder.getConditions());
            query.append(conditionsPart);
        }
        return query.toString();
    }
}
