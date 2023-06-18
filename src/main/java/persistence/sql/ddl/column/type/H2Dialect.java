package persistence.sql.ddl.column.type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H2Dialect implements Dialect {
    private final Map<Class<?>, String> h2TypeJavaClassMappings;

    public H2Dialect() {
        h2TypeJavaClassMappings = h2TypeJavaClassMappings();
    }

    private Map<Class<?>, String> h2TypeJavaClassMappings() {
        ConcurrentHashMap<Class<?>, String> h2TypeJavaClassMap = new ConcurrentHashMap<>();
        h2TypeJavaClassMap.put(Integer.class, "integer");
        h2TypeJavaClassMap.put(Long.class, "bigint");
        h2TypeJavaClassMap.put(String.class, "varchar");

        return h2TypeJavaClassMap;
    }

    @Override
    public String columnType(Class<?> clazz) {
        return h2TypeJavaClassMappings.get(clazz);
    }
}
