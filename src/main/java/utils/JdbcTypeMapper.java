package utils;

import java.sql.Types;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcTypeMapper {

    private static final ConcurrentHashMap<Class<?>, Integer> javaClassToJdbcTypeMappings = buildJavaClassToJdbcTypeCodeMappings();

    private static ConcurrentHashMap<Class<?>, Integer> buildJavaClassToJdbcTypeCodeMappings() {
        final ConcurrentHashMap<Class<?>, Integer> workMap = new ConcurrentHashMap<>();
        workMap.put(String.class, Types.VARCHAR);
        workMap.put(Integer.class, Types.INTEGER);
        workMap.put(Long.class, Types.BIGINT);

        return workMap;
    }

    public static Integer getJdbcTypeForClass(Class<?> javaClass) {
        return javaClassToJdbcTypeMappings.get(javaClass);
    }

}
