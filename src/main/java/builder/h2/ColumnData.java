package builder.h2;

public class ColumnData {

    private String columnName;
    private String columnDataType;
    private boolean isPrimaryKey;
    private boolean isNotNull;
    private boolean isAutoIncrement;

    public String getColumnName() {
        return columnName;
    }

    public String getColumnDataType() {
        return columnDataType;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    //PK 컬럼을 생성한다.
    public void createPk(String columnName, Class<?> columnDataType, boolean isAutoIncrement) {
        this.columnName = columnName;
        this.columnDataType = H2DataType.findH2DataTypeByDataType(columnDataType);
        this.isPrimaryKey = true;
        this.isNotNull = true;
        this.isAutoIncrement = isAutoIncrement;
    }

    //일반 컬럼을 생성한다.
    public void createColumn(String columnName, Class<?> columnDataType, boolean isNotNull) {
        this.columnName = columnName;
        this.columnDataType = H2DataType.findH2DataTypeByDataType(columnDataType);
        this.isPrimaryKey = false;
        this.isNotNull = isNotNull;
        this.isAutoIncrement = false;
    }
}
