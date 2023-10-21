package persistence.sql.dml.clause.operator;

public class AndOperator implements SqlOperator {

    @Override
    public String getOperatorSql() {
        return "AND";
    }
}
