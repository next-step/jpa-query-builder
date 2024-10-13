package builder.h2;

public class ColumnData {

    private String columnName;
    private String columnDataType;
    private boolean primaryKey;
    private boolean checkNull;
    private boolean autoIncrement;

    public ColumnData(String columnName, String columnDataType, boolean primaryKey, boolean checkNull, boolean autoIncrement) {
        this.columnName = columnName;
        this.columnDataType = columnDataType;
        this.primaryKey = primaryKey;
        this.checkNull = checkNull;
        this.autoIncrement = autoIncrement;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnDataType() {
        return columnDataType;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isCheckNull() {
        return checkNull;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }
}
