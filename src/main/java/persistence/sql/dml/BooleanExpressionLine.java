package persistence.sql.dml;

public class BooleanExpressionLine {
    private final BooleanExpression expression;
    private final LogicalOperator logicalOperator;

    public BooleanExpressionLine(BooleanExpression expression, LogicalOperator operator) {
        this.expression = expression;
        this.logicalOperator = operator;
    }

    public BooleanExpression getExpression() {
        return expression;
    }

    public LogicalOperator getLogicalOperator() {
        return logicalOperator;
    }
}
