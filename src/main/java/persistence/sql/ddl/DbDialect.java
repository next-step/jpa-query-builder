package persistence.sql.ddl;

import java.util.Map;

public enum DbDialect {
    LONG("bigint"),
    STRING("varchar"),
    INTEGER("int");

    private static final Map<Class<?>, DbDialect> JAVA_TO_SQL = Map.of(
            Long.class, LONG,
            String.class, STRING,
            Integer.class, INTEGER
    );

    public static DbDialect fromJavaType(Class<?> javaType) {
        return JAVA_TO_SQL.get(javaType);
    }

    private final String sqlType;


    DbDialect(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlType() {
        return sqlType;
    }

    public boolean isString() {
        return this == STRING;
    }

    public boolean isNotString() {
        return this != STRING;
    }
}
