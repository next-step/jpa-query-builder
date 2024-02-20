package persistence.sql.dialect;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class H2Dialect implements Dialect {

    private static final H2Dialect INSTANCE = new H2Dialect();

    private static final Map<Class<?>, String> COLUMN_TYPES = new HashMap<>();

    static {
        COLUMN_TYPES.put(Long.class, "BIGINT");
        COLUMN_TYPES.put(String.class, "VARCHAR");
        COLUMN_TYPES.put(Integer.class, "INTEGER");
    }

    public static H2Dialect getInstance() {
        return INSTANCE;
    }

    @Override
    public String getColumnType(Class<?> type) {
        return ofNullable(COLUMN_TYPES.get(type))
                .orElseThrow(() -> new IllegalArgumentException("This type is not supported."));
    }
}
