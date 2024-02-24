package persistence.sql.dml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BooleanBuilder {
    private final List<BooleanExpressionLine> expressionLines = new ArrayList<>();
    public BooleanBuilder() {
    }

    public void and(BooleanExpression expression) {
        expressionLines.add(new BooleanExpressionLine(expression, LogicalOperator.AND));
    }

    public List<BooleanExpressionLine> getExpressionLines() {
        return expressionLines;
    }

    public boolean isEmpty(){
        return expressionLines.isEmpty();
    }
}
