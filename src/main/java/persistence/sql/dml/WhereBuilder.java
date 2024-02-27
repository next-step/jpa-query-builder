package persistence.sql.dml;

import java.util.ArrayList;
import java.util.List;

public class WhereBuilder {
    private final List<BooleanExpressionLine> expressionLines = new ArrayList<>();
    public WhereBuilder() {
    }

    public void and(BooleanExpression expression) {
        expressionLines.add(new BooleanExpressionLine(expression, LogicalOperator.AND));
    }

    public String toClause() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean firstLine = true;

        for (BooleanExpressionLine line : expressionLines) {
            if (!firstLine) {
                stringBuilder.append(" ");
                stringBuilder.append(line.getLogicalOperator().name());
                stringBuilder.append(" ");
            }
            BooleanExpression expression = line.getExpression();
            stringBuilder.append(expression.getColumn());
            stringBuilder.append(" ");
            stringBuilder.append(expression.getOperator().getSymbol());
            stringBuilder.append(" ");
            stringBuilder.append(ValueUtil.getValueString(expression.getValue()));
            firstLine = false;
        }

        return stringBuilder.toString();
    }
    public boolean isEmpty(){
        return expressionLines.isEmpty();
    }
}
