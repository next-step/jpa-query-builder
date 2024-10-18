package builder.dml;

import util.StringUtil;

public class DMLColumnData {

    private final String columnName;
    private Class<?> columnType;
    private Object columnValue;
    private boolean isPrimaryKey;

    private DMLColumnData(String columnName, Class<?> columnType, Object columnValue, boolean isPrimaryKey) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.columnValue = columnValue;
        this.isPrimaryKey = isPrimaryKey;
    }

    private DMLColumnData(String columnName, Class<?> columnType, boolean isPrimaryKey) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.isPrimaryKey = isPrimaryKey;
    }

    public DMLColumnData(String columnName) {
        this.columnName = columnName;
    }

    public static DMLColumnData creatInstancePkColumn(String columnName, Class<?> columnType) {
        return new DMLColumnData(columnName, columnType, true);
    }

    public static DMLColumnData creatEntityPkColumn(String columnName, Class<?> columnType, Object columnValue) {
        return new DMLColumnData(columnName, columnType, columnValue, true);
    }

    public static DMLColumnData creatInstanceColumn(String columnName, Class<?> columnType, Object columnValue) {
        return new DMLColumnData(columnName, columnType, columnValue, false);
    }

    public static DMLColumnData createEntityColumn(String columnName) {
        return new DMLColumnData(columnName);
    }

    public String getColumnName() {
        return columnName;
    }

    public Class<?> getColumnType() {
        return columnType;
    }

    public Object getColumnValue() {
        return columnValue;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    //Value가 String.class 이면 작은따옴표로 묶어준다.
    public String getColumnValueByType() {
        if (this.columnType == String.class) {
            return StringUtil.wrapSingleQuote(this.columnValue);
        }

        return String.valueOf(this.columnValue);
    }
}
