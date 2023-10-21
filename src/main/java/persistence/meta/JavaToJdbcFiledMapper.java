package persistence.meta;

import java.sql.JDBCType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import persistence.exception.NotSupportTypeException;

public final class JavaToJdbcFiledMapper {
    private static final Map<Class<?>, JDBCType> classTypeMapper = new ConcurrentHashMap<>();
    private JavaToJdbcFiledMapper() {}

    static {
        classTypeMapper.put(Integer.class, JDBCType.INTEGER);
        classTypeMapper.put(int.class, JDBCType.INTEGER);
        classTypeMapper.put(Long.class, JDBCType.BIGINT);
        classTypeMapper.put(long.class, JDBCType.BIGINT);
        classTypeMapper.put(String.class, JDBCType.VARCHAR);
    }

    public static JDBCType convert(Class<?> javaType) {
        final JDBCType type = classTypeMapper.get(javaType);
        if (type == null) {
            throw new NotSupportTypeException();
        }
        return type;
    }
}
