package persistence.sql.domain;

public class MockCondition implements ColumnOperation {

    private final String columnName;
    private final String columnValue;

    MockCondition(String columnName, String columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    @Override
    public String getJdbcColumnName() {
        return columnName;
    }

    @Override
    public String getColumnValue() {
        return columnValue;
    }

    @Override
    public boolean hasColumnValue() {
        return true;
    }

    @Override
    public Class<?> getColumnObjectType() {
        return null;
    }

    @Override
    public Integer getColumnSize() {
        return null;
    }

    @Override
    public boolean isPrimaryColumn() {
        return false;
    }

    @Override
    public boolean isNullable() {
        return false;
    }

    @Override
    public String getJavaFieldName() {
        return null;
    }
}
