package persistence.sql.meta.EntityType;

import java.util.Arrays;

public enum H2ColumnType implements ColumnType {
    BIGINT(Long.class, "bigint"),
    VARCHAR(String.class, "varchar(255)"),
    INTEGER(Integer.class, "integer"),
    ;

    private final Class<?> javaType;
    private final String queryDefinition;

    H2ColumnType(Class<?> javaType, String columnType) {
        this.javaType = javaType;
        this.queryDefinition = columnType;
    }

    public static H2ColumnType of(Class<?> javaType) {
        return Arrays.stream(H2ColumnType.values())
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
