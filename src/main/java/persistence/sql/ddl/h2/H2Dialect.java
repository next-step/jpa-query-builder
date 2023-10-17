package persistence.sql.ddl.h2;

import persistence.sql.ddl.Dialect;

import java.util.HashMap;
import java.util.Map;

public class H2Dialect implements Dialect {
    private final Map<Class<?>, String> COLUMN_TYPES = new HashMap<>();

    public H2Dialect() {
        initializeType();
    }

    @Override
    public String getDialectType(Class<?> type) {
        return COLUMN_TYPES.get(type);
    }

    private void initializeType() {
        COLUMN_TYPES.put(Long.class, "bigint");
        COLUMN_TYPES.put(Integer.class, "int");
        COLUMN_TYPES.put(String.class, "varchar");
        COLUMN_TYPES.put(int.class, "int");
    }
}
