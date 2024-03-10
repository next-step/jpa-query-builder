package persistence.sql.dml;

import persistence.sql.metadata.ColumnMetadata;

public class WhereCondition {
    public static final String WHERE_CLAUSE_TEMPLATE = "%s %s %s";
    private final ColumnMetadata columnMetadata;
    private final String operator;
    private final Object value;

    public WhereCondition(ColumnMetadata columnMetadata, String operator, Object value) {
        this.columnMetadata = columnMetadata;
        this.operator = operator;
        this.value = value;
    }

    public String toSqlCondition() {
        return String.format(WHERE_CLAUSE_TEMPLATE, columnMetadata.getName(), operator, convertValue());
    }

    private String convertValue() {
        if (columnMetadata.getType().equals(String.class)) {
            return String.format("'%s'", value);
        }
        return value.toString();
    }
}
