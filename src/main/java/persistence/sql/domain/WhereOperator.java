package persistence.sql.domain;

public enum WhereOperator {
    EQUAL("="),
    NOT_EQUAL("!="),
    AND("and"),
    OR("or");

    private final String operator;

    WhereOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
