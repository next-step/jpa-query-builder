package persistence.sql.ddl.domain;

public class DatabaseColumn {

    private final String name;

    private final Class<?> javaType;

    private final Integer size;

    private final boolean nullable;

    public DatabaseColumn(String name, Class<?> javaType, Integer size, boolean nullable) {
        this.name = name;
        this.javaType = javaType;
        this.size = size;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public Integer getSize() {
        return size;
    }

    public boolean isNullable() {
        return nullable;
    }
}
