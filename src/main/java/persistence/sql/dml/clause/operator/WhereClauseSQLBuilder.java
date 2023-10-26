package persistence.sql.dml.clause.operator;

import persistence.sql.dml.clause.ChainingLogicalOperatorStandardWhereClause;
import persistence.sql.dml.clause.ChainingWhereClauses;
import persistence.sql.dml.clause.WhereClause;

public class WhereClauseSQLBuilder {
    private final WhereClause whereClause;

    public WhereClauseSQLBuilder(WhereClause whereClause) {
        this.whereClause = whereClause;
    }

    public String build() {
        /**
         * 1. 기본형태:
         *  {COLUMN} {OPERATOR} {VALUE}
         * 2. AND 형태:
         *  {COLUMN} {OPERATOR} {VALUE} AND
         *      {COLUMN} {OPERATOR} {VALUE}
         * 3. AND 형태 (여러조건):
         *  {COLUMN} {OPERATOR} {VALUE} AND (
         *      {COLUMN} {OPERATOR} {VALUE} {PRECEDENCE_OPERATOR}
         *      {COLUMN} {OPERATOR} {VALUE}
         *      )
         * 4. OR 형태:
         *  {COLUMN} {OPERATOR} {VALUE} OR
         *      {COLUMN} {OPERATOR} {VALUE}
         * 5. OR 형태 (여러조건):
         *  {COLUMN} {OPERATOR} {VALUE} OR (
         *      {COLUMN} {OPERATOR} {VALUE} {PRECEDENCE_OPERATOR}
         *      {COLUMN} {OPERATOR} {VALUE}
         *  )
         */
        if(whereClause == null) {
            return "";
        }

        return "WHERE " + build(whereClause, true);
    }

    private String build(ChainingLogicalOperatorStandardWhereClause parentWhereClause, boolean isRootClause) {
        if (parentWhereClause.isSingleClause()) {
            return isRootClause ? parentWhereClause.buildClauseWithoutPrecedence() : parentWhereClause.buildClause();
        }

        StringBuilder clause = new StringBuilder();
        ChainingWhereClauses additionalClauses = parentWhereClause.getAdditionalClauses();
        String startingPrecedenceOperator = parentWhereClause.precedenceOperator();
        clause.append(startingPrecedenceOperator)
                .append(isRootClause ? "" : " ")
                .append("(");

        clause.append(parentWhereClause.buildClauseWithoutPrecedence());

        additionalClauses.stream()
                .forEach(wc -> clause.append(" ")
                        .append(build(wc, false)));

        clause.append(")");

        return clause.toString();
    }
}
