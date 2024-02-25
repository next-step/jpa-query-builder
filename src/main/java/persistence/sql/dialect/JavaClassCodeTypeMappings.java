package persistence.sql.dialect;

import java.sql.Types;
import java.util.concurrent.ConcurrentHashMap;

public class JavaClassCodeTypeMappings {
    private JavaClassCodeTypeMappings() {
    }

    private static final ConcurrentHashMap<Class, Integer> javaClassToCodeTypeMap = new ConcurrentHashMap();

    static {
        javaClassToCodeTypeMap.put(Long.class, Types.BIGINT);
        javaClassToCodeTypeMap.put(Integer.class, Types.INTEGER);
        javaClassToCodeTypeMap.put(String.class, Types.VARCHAR);
    }

    public static Integer getJavaClassToJdbcCodeType(Class javaClassType) {
        return javaClassToCodeTypeMap.get(javaClassType);
    }
}
