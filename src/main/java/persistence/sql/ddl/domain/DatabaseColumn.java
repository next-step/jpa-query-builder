package persistence.sql.ddl.domain;

public class DatabaseColumn {

    private final ColumnName name;

    private final Class<?> javaType;

    private final ColumnLength size;

    private final ColumnNullable nullable;

    public DatabaseColumn(ColumnName name, Class<?> javaType, ColumnLength size, ColumnNullable nullable) {
        this.name = name;
        this.javaType = javaType;
        this.size = size;
        this.nullable = nullable;
    }

    public String getName() {
        return name.getName();
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public Integer getSize() {
        return size.getSize();
    }

    public boolean isNullable() {
        return this.nullable.isNullable();
    }
}
