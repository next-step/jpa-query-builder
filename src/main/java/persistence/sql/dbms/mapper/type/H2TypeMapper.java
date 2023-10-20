package persistence.sql.dbms.mapper.type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class H2TypeMapper implements TypeMapper {
    private enum H2TypeMap {
        BIGINT("BIGINT", Long.class, long.class),
        INT("INT", Integer.class, int.class),
        VARCHAR("VARCHAR", String.class);

        private final Class<?>[] javaType;
        private final String dbType;

        private static final Map<Class<?>, String> CACHE = new HashMap<>();

        static {
            Arrays.stream(H2TypeMap.values())
                    .forEach(h2Type -> {
                        for (Class<?> type : h2Type.javaType) {
                            CACHE.put(type, h2Type.dbType);
                        }
                    });
        }

        H2TypeMap(String dbType, Class<?>... javaType) {
            this.javaType = javaType;
            this.dbType = dbType;
        }

        public static String findDbType(Class<?> javaType) {
            if (CACHE.containsKey(javaType))
                return CACHE.get(javaType);
            else
                throw new IllegalArgumentException("No such type in H2Type : " + javaType);
        }
    }

    @Override
    public String create(Class<?> type) {
        return H2TypeMap.findDbType(type);
    }
}
