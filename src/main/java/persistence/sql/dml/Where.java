package persistence.sql.dml;

import persistence.sql.mapping.Column;
import persistence.sql.mapping.Value;

public class Where {

    private final Column column;

    private final Value value;

    private final LogicalOperator logicalOperator;

    private final WhereOperator whereOperator;

    public Where(Column column, Value value, LogicalOperator logicalOperator, WhereOperator whereOperator) {
        this.column = column;
        this.value = value;
        this.logicalOperator = logicalOperator;
        this.whereOperator = whereOperator;
    }

    public String getWhereClause() {
        return (this.logicalOperator.getOperator() + " " + this.column.getName() + " " + this.whereOperator.operatorClause(this.value.getValueClause())).trim();
    }
}
