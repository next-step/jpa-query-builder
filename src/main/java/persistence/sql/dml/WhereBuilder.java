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
        boolean isFirstLine = true;

        for (BooleanExpressionLine line : expressionLines) {
            stringBuilder.append(line.toQuery(isFirstLine));
            isFirstLine = false;
        }

        return stringBuilder.toString();
    }

    public boolean isEmpty(){
        return expressionLines.isEmpty();
    }
}
