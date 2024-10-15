package persistence.sql.ddl;

import java.util.Arrays;

public enum JPAColumnType implements ColumnType {
    BIGINT(Long.class, "bigint"),
    VARCHAR(String.class, "varchar(255)"),
    INTEGER(Integer.class, "integer"),
    ;

    private final Class<?> javaType;
    private final String queryDefinition;

    JPAColumnType(Class<?> javaType, String columnType) {
        this.javaType = javaType;
        this.queryDefinition = columnType;
    }

    public static JPAColumnType of(Class<?> javaType) {
        return Arrays.stream(JPAColumnType.values())
                .filter(type -> type.getJavaType() == javaType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(javaType + "과 매칭되는 타입이 존재하지 않습니다."));
    }

    public String getQueryDefinition() {
        return queryDefinition;
    }

    public Class<?> getJavaType() {
        return javaType;
    }
}
