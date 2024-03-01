package persistence.sql.ddl.model;

import java.sql.Types;
import java.util.Arrays;

public enum Type {
    VARCHAR(String.class, Types.VARCHAR),
    INTEGER(Integer.class, Types.INTEGER),
    BIGINT(Long.class, Types.BIGINT);

    public Class<?> javaType;
    public int value;

    Type(Class<?> javaType, int value) {
        this.javaType = javaType;
        this.value = value;
    }

    public static Type from(Class<?> javaType) {
        return Arrays.stream(values())
                .filter(value -> javaType == value.javaType)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
