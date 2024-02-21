package persistence.sql;

import java.sql.Types;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcUtils {
    private static final Map<Class, Integer> workMap = new ConcurrentHashMap<>() {{
        put(String.class, Types.VARCHAR);
        put(Integer.class, Types.INTEGER);
        put(Long.class, Types.BIGINT);
    }};

    public static Integer convertJavaClassToJdbcTypeCode(Class clazz) {
        Integer code = workMap.get(clazz);
        if (code == null) {
            throw new IllegalArgumentException("No JdbcTypeCode mapping for class: " + clazz.getSimpleName());
        }
        return code;
    }
}
