package persistence.dialect;

import java.util.Map;

public class H2Dialect extends Dialect {
    @Override
    protected Map<Class<?>, String> initTypeRegistry() {
        return Map.of(
                String.class, "VARCHAR",
                Integer.class, "INTEGER",
                Long.class, "BIGINT"
        );
    }
}
