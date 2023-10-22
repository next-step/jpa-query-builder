package persistence.sql.dml.clause.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.dml.clause.WherePredicate;
import persistence.sql.dml.clause.operator.AndOperator;
import persistence.sql.dml.clause.operator.LogicalOperator;
import persistence.sql.dml.clause.operator.OrOperator;
import persistence.sql.exception.PreconditionRequiredException;

public class WhereClauseBuilder {

    private static final String WHERE_FORMAT = "WHERE %s";
    private static final String WHERE_PREDICATE_FORMAT = "%s %s";
    private static final String EMPTY_STRING = "";
    private static final LogicalOperator andOperator = new AndOperator();
    private static final LogicalOperator orOperator = new OrOperator();

    private final List<WhereClause> whereClauseList;

    private WhereClauseBuilder(WhereClause whereClause) {
        if (whereClause == null) {
            throw new PreconditionRequiredException("WhereClause required");
        }
        this.whereClauseList = new ArrayList<>(List.of(whereClause));
    }

    public static WhereClauseBuilder builder(WherePredicate wherePredicate) {
        return new WhereClauseBuilder(new WhereClause(null, wherePredicate));
    }

    public WhereClauseBuilder and(WherePredicate predicate) {
        this.whereClauseList.add(new WhereClause(andOperator, predicate));
        return this;
    }

    public WhereClauseBuilder or(WherePredicate predicate) {
        this.whereClauseList.add(new WhereClause(orOperator, predicate));
        return this;
    }

    public String build() {
        if (isEmptyWhereClause()) {
            return EMPTY_STRING;
        }

        final String predicateJoiningString = whereClauseList.stream()
            .map(WhereClauseBuilder::formatPredicate)
            .collect(Collectors.joining(" "));

        return String.format(WHERE_FORMAT, predicateJoiningString);
    }

    private static String formatPredicate(WhereClause whereClause) {
        if (whereClause.getOperatorSql().isEmpty()) {
            return whereClause.getPredicateCondition();
        }

        return String.format(WHERE_PREDICATE_FORMAT, whereClause.getOperatorSql(), whereClause.getPredicateCondition());
    }

    public boolean isEmptyWhereClause() {
        return this.whereClauseList.isEmpty();
    }

    private static class WhereClause {

        private final LogicalOperator operator;
        private final WherePredicate predicate;

        public WhereClause(LogicalOperator operator, WherePredicate predicate) {
            this.operator = operator;
            this.predicate = predicate;
        }

        public String getOperatorSql() {
            if (operator == null) {
                return EMPTY_STRING;
            }

            return operator.getOperatorSql();
        }

        public String getPredicateCondition() {
            if (predicate == null) {
                return null;
            }

            return predicate.toCondition();
        }
    }
}
