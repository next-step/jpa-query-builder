package builder.h2;

public class ColumnData {

    private final String columnName;
    private final String columnDataType;
    private final boolean isPrimaryKey;
    private final boolean isNotNull;
    private final boolean isAutoIncrement;

    public ColumnData(String columnName, String columnDataType, boolean isPrimaryKey, boolean isNotNull, boolean isAutoIncrement) {
        this.columnName = columnName;
        this.columnDataType = columnDataType;
        this.isPrimaryKey = isPrimaryKey;
        this.isNotNull = isNotNull;
        this.isAutoIncrement = isAutoIncrement;
    }

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
    public static ColumnData createPk(String columnName, Class<?> columnDataType, boolean isAutoIncrement) {
        return new ColumnData(
                columnName,
                H2DataType.findH2DataTypeByDataType(columnDataType),
                true,
                true,
                isAutoIncrement
        );
    }

    //일반 컬럼을 생성한다.
    public static ColumnData createColumn(String columnName, Class<?> columnDataType, boolean isNotNull) {
        return new ColumnData(
                columnName,
                H2DataType.findH2DataTypeByDataType(columnDataType),
                false,
                isNotNull,
                false
        );
    }
}
