package persistence.sql.dml;

public class BooleanExpression {
    private final String column;
    private final Object value;
    private final ComparisonOperator operator;

    private BooleanExpression(String column, Object value, ComparisonOperator operator) {
        this.column = column;
        this.value = value;
        this.operator = operator;
    }

    public static BooleanExpression eq(String column, Object value) {
        return new BooleanExpression(column, value, ComparisonOperator.EQUAL);
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return value;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }
}
