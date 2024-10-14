package H2QueryBuilder;

public class ColumnData {
    private final String columnName;
    private final String columnDataType;
    private final boolean isPrimeKey;
    private final boolean isNotNull;
    private final boolean isAutoIncrement;

    public ColumnData(String columnName, Class<?> columnDataType, boolean isPrimeKey, boolean isNotNull, boolean isAutoIncrement) {
        this.columnName = columnName;
        this.columnDataType = H2DataType.findH2DataTypeByDataType(columnDataType);
        this.isPrimeKey = isPrimeKey;
        this.isNotNull = isNotNull;
        this.isAutoIncrement = isAutoIncrement;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnDataType() {
        return columnDataType;
    }

    public boolean isPrimeKey() {
        return isPrimeKey;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }
}
