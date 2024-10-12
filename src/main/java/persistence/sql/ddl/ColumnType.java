package persistence.sql.ddl;

import java.util.Arrays;

public enum ColumnType {
    BIGINT(Long.class, "bigint"),
    INTEGER(Integer.class, "integer"),
    VARCHAR(String.class, "varchar(255)"),
    ;

    private final Class<?> javaType;
    private final String sqlType;

    ColumnType(Class<?> javaType, String sqlType) {
        this.javaType = javaType;
        this.sqlType = sqlType;
    }

    public static ColumnType of(Class<?> javaType) {
        return Arrays.stream(values())
                .filter(v -> v.javaType.equals(javaType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No column type found for " + javaType));
    }

    public String getSqlType() {
        return sqlType;
    }
}
