package persistence.sql.dml.clause;

import persistence.sql.dml.clause.operator.Operator;
import persistence.sql.dml.clause.operator.PrecedenceOperator;

public abstract class DefaultStandardWhereClause implements StandardWhereClause {
    protected final String columnName;
    protected final String value;
    protected final Operator operator;
    protected PrecedenceOperator precedenceOperator;

    public DefaultStandardWhereClause(String columnName, String value, Operator operator, PrecedenceOperator precedenceOperator) {
        this.columnName = columnName;
        this.value = value;
        this.operator = operator;
        this.precedenceOperator = precedenceOperator;
    }

    @Override
    public String column() {
        return columnName;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String operator() {
        return operator.sqlOperator();
    }

    @Override
    public String precedenceOperator() {
        return precedenceOperator.sqlOperator();
    }

}
