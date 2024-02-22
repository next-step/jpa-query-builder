package persistence.sql.domain;

public class DatabaseColumn {

    private final ColumnName name;

    private final ColumnValue value;

    private final ColumnLength size;

    private final ColumnNullable nullable;

    public DatabaseColumn(ColumnName name, ColumnValue value, ColumnLength size, ColumnNullable nullable) {
        this.name = name;
        this.value = value;
        this.size = size;
        this.nullable = nullable;
    }

    public String getName() {
        return name.getName();
    }

    public Class<?> getJavaType() {
        return value.getJavaType();
    }

    public Integer getSize() {
        return size.getSize();
    }

    public boolean isNullable() {
        return this.nullable.isNullable();
    }

    public String getValue() {
        return value.getValue();
    }
}
