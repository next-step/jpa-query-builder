package persistence.sql.domain;

public class Condition {

    private final String condition;

    private Condition(String condition) {
        this.condition = condition;
    }

    public static Condition equal(ColumnOperation column, Object value) {
        String condition = column.getJdbcColumnName() + WhereOperator.EQUAL.getOperator() + value;
        return new Condition(condition);
    }

    public static Condition equal(ColumnOperation column) {
        return equal(column, column.getColumnValue());
    }

    public String getCondition() {
        return condition;
    }
}
