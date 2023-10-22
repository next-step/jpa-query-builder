package persistence.sql.dml.clause.operator;

public class NotEqualOperator implements ComparisonOperator {

    @Override
    public String getOperatorSql() {
        return "!=";
    }
}
