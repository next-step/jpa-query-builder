package persistence.sql.dialect;

import java.util.HashMap;
import java.util.Map;

public abstract class Dialect {

    protected static final Map<Class<?>, String> typeMap = new HashMap<>();

    public abstract String getAutoIncrementDefinition();

    public String getSqlTypeDefinition(Class<?> clazz) {
        if (!typeMap.containsKey(clazz)) {
            throw new IllegalArgumentException("존재하지 않은 타입입니다.");
        }
        return typeMap.get(clazz);
    }
}
