package persistence.sql.ddl.dialect.h2;

import persistence.sql.ddl.dialect.database.TypeMapper;
import persistence.sql.ddl.dialect.exception.InvalidJavaClassException;
import persistence.sql.ddl.query.model.ColumnType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H2TypeMapper implements TypeMapper {

    private final Map<Class<?>, String> javaClassToJdbcType;

    private H2TypeMapper(final Map<Class<?>, String> workMap) {
        this.javaClassToJdbcType = workMap;
    }

    public static H2TypeMapper newInstance() {
        Map<Class<?>, String> workMap = new ConcurrentHashMap<>();

        workMap.put(String.class, ColumnType.VARCHAR.name());
        workMap.put(Boolean.class, ColumnType.BOOLEAN.name());
        workMap.put(Integer.class, ColumnType.INTEGER.name());
        workMap.put(Long.class, ColumnType.BIGINT.name());

        return new H2TypeMapper(workMap);
    }

    @Override
    public String toSqlType(Class<?> clazz) {
        if(!this.javaClassToJdbcType.containsKey(clazz)) {
            throw new InvalidJavaClassException();
        }

        return this.javaClassToJdbcType.get(clazz);
    }
}
