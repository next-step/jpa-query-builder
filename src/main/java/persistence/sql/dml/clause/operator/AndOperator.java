package persistence.sql.dml.clause.operator;

public class AndOperator implements LogicalOperator {

    @Override
    public String getOperatorSql() {
        return "AND";
    }
}
