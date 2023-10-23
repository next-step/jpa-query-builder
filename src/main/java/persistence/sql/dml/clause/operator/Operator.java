package persistence.sql.dml.clause.operator;

public enum Operator {
    EQUALS("="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_OR_EQUALS(">="),
    LESS_THAN_OR_EQUALS("<="),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE"),
    IN("IN"),
    NOT_IN("NOT IN"),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL")
    ;

    private final String sqlOperator;

    Operator(String sqlOperator) {
        this.sqlOperator = sqlOperator;
    }

    public String sqlOperator() {
        return sqlOperator;
    }
}
