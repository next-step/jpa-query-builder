package persistence.sql.dml.value;

public class IdValue {
    private final String columnName;
    private final String value;
    private final String strategy;

    public IdValue(String columnName, String value, String strategy) {
        this.columnName = columnName;
        this.value = value;
        this.strategy = strategy;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getValue() {
        return value;
    }

    public String getStrategy() {
        return strategy;
    }
}
