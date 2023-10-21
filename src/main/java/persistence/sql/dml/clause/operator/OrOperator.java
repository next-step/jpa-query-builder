package persistence.sql.dml.clause.operator;

public class OrOperator implements SqlOperator {

    @Override
    public String getOperatorSql() {
        return "OR";
    }
}
