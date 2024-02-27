package persistence.sql.domain;

import java.lang.reflect.Field;

public class DatabaseColumn implements ColumnOperation {

    protected final ColumnName name;

    protected final ColumnValue value;

    protected final ColumnLength size;

    protected final ColumnNullable nullable;

    protected DatabaseColumn(ColumnName name, ColumnValue value, ColumnLength size, ColumnNullable nullable) {
        this.name = name;
        this.value = value;
        this.size = size;
        this.nullable = nullable;
    }

    public static DatabaseColumn fromField(Field field, Object object) {
        ColumnName name = new ColumnName(field);
        ColumnLength length = new ColumnLength(field);
        ColumnValue value = new ColumnValue(field, object);
        ColumnNullable nullable = ColumnNullable.getInstance(field);
        return new DatabaseColumn(name, value, length, nullable);
    }

    public static DatabaseColumn copy(DatabaseColumn databaseColumn) {
        return new DatabaseColumn(databaseColumn.name, databaseColumn.value, databaseColumn.size, databaseColumn.nullable);
    }

    @Override
    public String getJdbcColumnName() {
        return name.getJdbcColumnName();
    }

    @Override
    public String getColumnValue() {
        return value.getValue();
    }

    @Override
    public boolean hasColumnValue() {
        return value.getValue() != null;
    }

    @Override
    public Class<?> getColumnObjectType() {
        return value.getColumnObjectType();
    }

    @Override
    public Integer getColumnSize() {
        return size.getSize();
    }

    @Override
    public boolean isPrimaryColumn() {
        return false;
    }

    @Override
    public String getJavaFieldName() {
        return name.getJavaFieldName();
    }

    @Override
    public boolean isNullable() {
        return this.nullable.isNullable();
    }

}
