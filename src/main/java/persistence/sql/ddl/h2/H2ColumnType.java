package persistence.sql.ddl.h2;

import java.util.Arrays;

public enum H2ColumnType {
    BIGINT(Long.class, "BIGINT"),
    INT(Integer.class, "INT"),
    VARCHAR(String.class, "VARCHAR")
    ;

    private final Class<?> javaType;
    private final String sqlType;

    H2ColumnType(Class<?> javaType, String sqlType) {
        this.javaType = javaType;
        this.sqlType = sqlType;
    }

    public static String getSqlType(Class<?> javaType) {
        return findByJavaType(javaType).sqlType;
    }

    private static H2ColumnType findByJavaType(Class<?> javaType) {
        return Arrays.stream(H2ColumnType.values())
                .filter(columnType -> columnType.javaType == javaType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("타입이 존재하지 않습니다."));
    }

}
