package persistence.sql.ddl;

import java.sql.JDBCType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JavaJdbcFiledMapper {
    private static final Map<Class<?>, JDBCType> classTypeMapper = new ConcurrentHashMap<>();

    public JavaJdbcFiledMapper() {
        classTypeMapper.put(Integer.class, JDBCType.INTEGER);
        classTypeMapper.put(int.class, JDBCType.INTEGER);
        classTypeMapper.put(Long.class, JDBCType.BIGINT);
        classTypeMapper.put(long.class, JDBCType.BIGINT);
        classTypeMapper.put(String.class, JDBCType.VARCHAR);
    }

    public JDBCType convert(Class<?> javaType) {
        final JDBCType jdbcType = classTypeMapper.get(javaType);
        if (jdbcType == null) {
            throw new IllegalArgumentException("지원 하지 않은 타입 입니다.");
        }
        return classTypeMapper.get(javaType);
    }


}
