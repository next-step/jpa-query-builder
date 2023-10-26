package persistence.sql.dml.clause;

import persistence.sql.dml.clause.operator.Operator;
import persistence.sql.dml.clause.operator.PrecedenceOperator;

public abstract class ChainingLogicalOperatorStandardWhereClause extends DefaultStandardWhereClause {

    protected ChainingLogicalOperatorStandardWhereClause(String columnName, String value, Operator operator, PrecedenceOperator precedenceOperator) {
        super(columnName, value, operator, precedenceOperator);
    }

    private final ChainingWhereClauses additionalClauses = new ChainingWhereClauses();

    private void and() {
        super.precedenceOperator = PrecedenceOperator.AND;
    }

    private void or() {
        super.precedenceOperator = PrecedenceOperator.OR;
    }

    public final <T extends ChainingLogicalOperatorStandardWhereClause> T and(ChainingLogicalOperatorStandardWhereClause standardWhereClause) {
        standardWhereClause.and();
        additionalClauses.add(standardWhereClause);
        return (T) this;
    }

    public final <T extends ChainingLogicalOperatorStandardWhereClause> T or(ChainingLogicalOperatorStandardWhereClause standardWhereClause) {
        standardWhereClause.or();
        additionalClauses.add(standardWhereClause);
        return (T) this;
    }

    public boolean isSingleClause() {
        return additionalClauses.isEmpty();
    }

    public ChainingWhereClauses getAdditionalClauses() {
        return additionalClauses;
    }
}
