package persistence.sql.dml.clause;

public interface StandardWhereClause {
    String column();

    String operator();
    String precedenceOperator();

    String value();

    default String buildClause() {
        return precedenceOperator() + " " + column() + " " + operator() + " " + value();
    }

    default String buildClauseWithoutPrecedence() {
        return column() + " " + operator() + " " + value();
    }
}
