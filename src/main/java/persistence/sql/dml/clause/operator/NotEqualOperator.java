package persistence.sql.dml.clause.operator;

public class NotEqualOperator implements SqlOperator {

    @Override
    public String getOperatorSql() {
        return "!=";
    }
}
