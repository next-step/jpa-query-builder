package persistence.sql.dml.clause.operator;

public class EqualOperator implements SqlOperator {

    @Override
    public String getOperatorSql() {
        return "=";
    }
}
