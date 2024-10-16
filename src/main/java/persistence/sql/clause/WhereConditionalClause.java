package persistence.sql.clause;

import java.util.List;
import java.util.stream.Collectors;

public record WhereConditionalClause(String column, String value, String operator) implements ConditionalClause {

    public static WhereExpression builder() {
        return new WhereExpression();
    }

    public static class WhereExpression {
        private String column;
        private String value;
        private String operator;

        public WhereExpression column(String column) {
            this.column = column;
            return this;
        }

        public WhereConditionalClause eq(String value) {
            this.value = value;
            this.operator = "=";
            return build();
        }

        public WhereConditionalClause in(List<Object> values) {
            this.value = values.stream()
                    .map(Clause::toColumnValue)
                    .collect(Collectors.joining(" , "));
            this.operator = "IN";
            return build();
        }

        public WhereConditionalClause neq(String value) {
            this.value = value;
            this.operator = "!=";
            return build();
        }

        public WhereConditionalClause notIn(List<Object> values) {
            this.value = values.stream()
                    .map(Clause::toColumnValue)
                    .collect(Collectors.joining(" , "));
            this.operator = "NOT IN";
            return build();
        }

        public WhereConditionalClause build() {
            return new WhereConditionalClause(column, value, operator);
        }
    }
}