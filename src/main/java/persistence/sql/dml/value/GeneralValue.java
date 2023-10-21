package persistence.sql.dml.value;

public class GeneralValue {
    private final String columnName;
    private final String value;

    public GeneralValue(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getValue() {
        return value;
    }
}
