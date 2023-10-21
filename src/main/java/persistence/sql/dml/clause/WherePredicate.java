package persistence.sql.dml.clause;

import persistence.sql.dml.clause.operator.SqlOperator;
import persistence.sql.schema.EntityObjectMappingMeta;

public class WherePredicate {

    public static final String PREDICATE_FORMAT = "%s %s %s";

    private final String columnName;
    private final SqlOperator whereOperator;
    private final Object whereValue;

    private WherePredicate(String columnName, Object whereValue, SqlOperator whereOperator) {
        this.columnName = columnName;
        this.whereValue = whereValue;
        this.whereOperator = whereOperator;
    }

    public static WherePredicate of(String columnName, Object whereValue, SqlOperator whereOperator) {
        return new WherePredicate(columnName, whereValue, whereOperator);
    }

    public String toCondition() {
        return String.format(PREDICATE_FORMAT, columnName, whereOperator.getOperatorSql(),
            EntityObjectMappingMeta.formatValueAsString(whereValue)
        );
    }
}
