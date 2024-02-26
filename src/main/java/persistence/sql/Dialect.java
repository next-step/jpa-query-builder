package persistence.sql;

import persistence.sql.domain.ColumnOperation;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public abstract class Dialect {

    protected final Map<Integer, Class<?>> jdbcTypeToJavaClass = new HashMap<>();
    protected final Map<Class<?>, Integer> javaClassToJdbcType = new HashMap<>();

    protected Dialect() {
        registerType(Types.BIGINT, Long.class);
        registerType(Types.INTEGER, Integer.class);
        registerType(Types.VARCHAR, String.class);
    }

    private void registerType(Integer jdbcType, Class<?> javaType) {
        jdbcTypeToJavaClass.put(jdbcType, javaType);
        javaClassToJdbcType.put(javaType, jdbcType);
    }

    public abstract String getJdbcTypeFromJavaClass(ColumnOperation column);
}
