package persistence.sql.dml.where;

public enum WhereClauseType {
    OR("or"),
    AND("and"),
    EQUALS("="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_OR_EQUALS(">="),
    LESS_THAN_OR_EQUALS("<="),
    LIKE("like"),
    IN("in"),
    NOT_IN("not in"),
    IS_NULL("is null"),
    IS_NOT_NULL("is not null"),
    NONE("");

    private final String operator;

    WhereClauseType(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
