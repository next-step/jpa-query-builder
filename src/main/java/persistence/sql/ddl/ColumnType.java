package persistence.sql.ddl;

import java.util.Arrays;

public enum ColumnType {
    BIGINT(Long.class, "bigint"),
    VARCHAR(String.class, "varchar(255)"),
    INTEGER(Integer.class, "integer"),
    ;

    private final Class<?> javaType;
    private final String columnType;

    ColumnType(Class<?> javaType, String columnType) {
        this.javaType = javaType;
        this.columnType = columnType;
    }

    public static ColumnType of(Class<?> javaType) throws Exception {
        return Arrays.stream(ColumnType.values())
                .filter(type -> type.getJavaType() == javaType)
                .findFirst()
                .orElseThrow(Exception::new);
    }

    public String getColumnType() {
        return columnType;
    }

    public Class<?> getJavaType() {
        return javaType;
    }
}
