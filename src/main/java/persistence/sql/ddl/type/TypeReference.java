package persistence.sql.ddl.type;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TypeReference {

    LONG(Long.class, "bigint"),
    STRING(String.class, "varchar"),
    INTEGER(Integer.class, "integer")
    ;

    private final Class<?> javaType;
    private final String sqlType;
    private static final Map<Class<?>, String> sqlTypes;

    static {
        sqlTypes = Arrays.stream(values())
                .collect(Collectors.toConcurrentMap(reference -> reference.javaType, reference -> reference.sqlType));
    }

    TypeReference(Class<?> javaType, String sqlType) {
        this.javaType = javaType;
        this.sqlType = sqlType;
    }

    public static String getSqlType(Class<?> clazz) {
        if (!sqlTypes.containsKey(clazz)) {
            throw new RuntimeException("class '" + clazz.getName() + "' sql type not exist.");
        }
        return sqlTypes.get(clazz);
    }

}
