package persistence.sql.meta.column;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ColumnType {
    private final Integer type;
    private static final Map<Class, Integer> CLASS_TYPE_MAP = new ConcurrentHashMap<>() {{
        put(String.class, Types.VARCHAR);
        put(Integer.class, Types.INTEGER);
        put(Long.class, Types.BIGINT);
    }};

    public ColumnType(Field field) {
        this(convertJavaClassToJdbcTypeCode(field.getType()));
    }

    private ColumnType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static Integer convertJavaClassToJdbcTypeCode(Class clazz) {
        Integer code = CLASS_TYPE_MAP.get(clazz);
        if (code == null) {
            throw new IllegalArgumentException("No JdbcTypeCode mapping for class: " + clazz.getSimpleName());
        }
        return code;
    }

}
