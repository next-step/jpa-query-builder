package H2QueryBuilder;

import java.util.HashMap;
import java.util.Map;

public enum H2DataType {
    LONG(Long.class, "BIGINT"),
    STRING(String.class, "VARCHAR"),
    INTEGER(Integer.class, "INT");

    private static final Map<Class<?>, H2DataType> typeMappingMap = new HashMap<>();

    static {
        for (H2DataType type : H2DataType.values()) {
            typeMappingMap.put(type.javaType, type);
        }
    }

    private final Class<?> javaType;
    private final String sqlType;

    H2DataType(Class<?> javaType, String sqlType) {
        this.javaType = javaType;
        this.sqlType  = sqlType;
    }

    public static H2DataType getMapping(Class<?> javaType) {
        return typeMappingMap.get(javaType);
    }

    public String getSqlType() {
        return sqlType;
    }
}
