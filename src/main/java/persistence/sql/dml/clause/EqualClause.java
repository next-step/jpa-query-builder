package persistence.sql.dml.clause;

import persistence.model.EntityColumn;
import persistence.sql.dialect.Dialect;

public class EqualClause implements Clause {
    private final EntityColumn targetColumn;

    private final Object findingValue;

    public EqualClause(EntityColumn targetColumn, Object findingValue) {
        this.targetColumn = targetColumn;
        this.findingValue = findingValue;
    }

    @Override
    public String toSql(Dialect dialect) {
        return dialect.getIdentifierQuoted(targetColumn.getName()) +
                " = " +
                dialect.getValueQuoted(findingValue);
    }
}
