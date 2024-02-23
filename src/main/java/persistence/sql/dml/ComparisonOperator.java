package persistence.sql.dml;

public class ComparisonOperator extends WhereOperator {

    public enum Comparisons {
        EQ("="), NE("<>"), GT(">"),
        GE(">="), LT("<"), LE("<="),
        ;

        private final String operator;

        Comparisons(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }
    }

    public ComparisonOperator(Comparisons comparison) {
        this.comparison = comparison;
    }

    private final Comparisons comparison;

    @Override
    String operatorClause(final String value) {
        return this.comparison.operator + " " + value;
    }
}
