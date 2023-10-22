package persistence.sql.dml.clause.operator;

public class OrOperator implements LogicalOperator {

    @Override
    public String getOperatorSql() {
        return "OR";
    }
}
