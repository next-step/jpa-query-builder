package persistence.sql.dml.clause;

import persistence.sql.SqlValueMapper;
import persistence.sql.dml.clause.operator.Operator;
import persistence.sql.dml.clause.operator.PrecedenceOperator;

import java.util.Set;

public class WhereClause extends ChainingLogicalOperatorStandardWhereClause {
    private static final Set<Operator> NULL_OPERATORS = Set.of(Operator.IS_NULL, Operator.IS_NOT_NULL);

    private WhereClause(String columnName, String value, Operator operator, PrecedenceOperator precedenceOperator) {
        super(columnName, value, operator, precedenceOperator);
    }

    public static WhereClause of(String columnName, Object value, Operator operator) {
        if (NULL_OPERATORS.contains(operator)) {
            return fromNullValue(columnName, operator, PrecedenceOperator.NONE);
        }

        return new WhereClause(columnName, SqlValueMapper.toSqlValue(value), operator, PrecedenceOperator.NONE);
    }

    private static WhereClause fromNullValue(String columnName, Operator operator, PrecedenceOperator precedenceOperator) {
        return new WhereClause(columnName, SqlValueMapper.toSqlValue(""), operator, precedenceOperator);
    }
}
