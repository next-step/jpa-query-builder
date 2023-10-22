package persistence.sql.dml.clause.operator;

public class EqualOperator implements ComparisonOperator {

    @Override
    public String getOperatorSql() {
        return "=";
    }
}
