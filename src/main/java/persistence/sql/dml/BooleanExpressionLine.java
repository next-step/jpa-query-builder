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

    public String toQuery(boolean firstLine) {
        StringBuilder stringBuilder = new StringBuilder();

        if (!firstLine) {
            stringBuilder.append(" ");
            stringBuilder.append(logicalOperator.name());
            stringBuilder.append(" ");
        }

        stringBuilder.append(expression.getColumn());
        stringBuilder.append(" ");
        stringBuilder.append(expression.getOperator().getSymbol());
        stringBuilder.append(" ");
        stringBuilder.append(ValueUtil.getValueString(expression.getValue()));

        return stringBuilder.toString();
    }
}
