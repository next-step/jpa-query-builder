package persistence.sql.dialet.h2;

import java.util.Arrays;

public enum H2TypeDialect {

    VARCHAR(String.class, "VARCHAR"),
    INTEGER(Integer.class, "INTEGER"),
    BIGINT(Long.class, "BIGINT");

    private final Class<?> javaType;
    private final String sqlType;

    H2TypeDialect(Class<?> javaType, String sqlType) {
        this.javaType = javaType;
        this.sqlType = sqlType;
    }


    public Class<?> getJavaType() {
        return javaType;
    }

    public String getSqlType() {
        return sqlType;
    }

    public static H2TypeDialect getDialect(Class<?> clazz) {
        return Arrays.stream(H2TypeDialect.values())
            .filter(dialect -> dialect.javaType.equals(clazz))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 타입니다."));
    }
}
