package utils;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import persistence.sql.ddl.scheme.Schemes;

import java.sql.Types;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcTypeMapper {

    private static final ConcurrentHashMap<Class<?>, Integer> javaClassToJdbcTypeMappings = buildJavaClassToJdbcTypeCodeMappings();

    private static ConcurrentHashMap<Class<?>, Integer> buildJavaClassToJdbcTypeCodeMappings() {
        final ConcurrentHashMap<Class<?>, Integer> workMap = new ConcurrentHashMap<>();
        workMap.put(String.class, Types.VARCHAR);
        workMap.put(Integer.class, Types.INTEGER);
        workMap.put(Long.class, Types.BIGINT);

        workMap.put(Id.class, Schemes.Id);
        workMap.put(Column.class, Schemes.Column);
        workMap.put(GeneratedValue.class, Schemes.GeneratedValue);

        return workMap;
    }

    public static Integer getJdbcTypeForClass(Class<?> javaClass) {
        return javaClassToJdbcTypeMappings.get(javaClass);
    }

}
