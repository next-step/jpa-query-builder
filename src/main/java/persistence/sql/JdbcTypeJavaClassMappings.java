package persistence.sql;

import java.sql.Types;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcTypeJavaClassMappings {
    private static final JdbcTypeJavaClassMappings INSTANCE = new JdbcTypeJavaClassMappings();

    private final ConcurrentHashMap<Integer, Class<?>> jdbcCodeToJavaClassMappings;
    private final ConcurrentHashMap<Class<?>, Integer> javaClassToJdbcCodeMappings;

    public JdbcTypeJavaClassMappings() {
        this.jdbcCodeToJavaClassMappings = new ConcurrentHashMap<>();
        this.javaClassToJdbcCodeMappings = new ConcurrentHashMap<>();

        javaClassToJdbcCodeMappings.put(String.class, Types.VARCHAR);
        javaClassToJdbcCodeMappings.put(Long.class, Types.BIGINT);
        javaClassToJdbcCodeMappings.put(Integer.class, Types.INTEGER);

        jdbcCodeToJavaClassMappings.put(Types.VARCHAR, String.class);
        jdbcCodeToJavaClassMappings.put(Types.BIGINT, Long.class);
        jdbcCodeToJavaClassMappings.put(Types.INTEGER, Integer.class);
    }

    public static Class<?> getJdbcCodeToJavaClass(Integer jdbcCode) {
        return INSTANCE.jdbcCodeToJavaClassMappings.get(jdbcCode);
    }

    public static Integer getJavaClassToJdbcCode(Class<?> clazz) {
        return INSTANCE.javaClassToJdbcCodeMappings.get(clazz);
    }
}
