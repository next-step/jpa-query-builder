package persistence.sql.dialect;

import java.util.HashMap;
import java.util.Map;

public abstract class TypeDialect {

    private final Map<Class<?>, String> types = new HashMap<>();

    protected void registerType(Class<?> javaType, String sqlType) {
        types.put(javaType, sqlType);
    }

    public String getSqlType(Class<?> javaType) {
        String sqlType = types.get(javaType);
        if (sqlType == null) {
            throw new IllegalArgumentException("타입이 존재하지 않습니다.");
        }
        return sqlType;
    }

}
