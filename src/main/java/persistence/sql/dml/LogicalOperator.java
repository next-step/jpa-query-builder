package persistence.sql.dml;

public enum LogicalOperator {
    AND("and"), OR("or"), NOT("not"), NONE("");

    private final String operator;

    LogicalOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
