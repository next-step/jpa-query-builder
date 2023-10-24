package persistence.sql;

import persistence.exception.NotFoundSqlTypeException;

import java.sql.Types;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcTypeJavaClassMapping {

    private final Map<Integer, String> sqlTypeToJavaClassMap = new ConcurrentHashMap<>();

    private final Map<Class<?>, Integer> javaClassToTypeMappingMap = new ConcurrentHashMap<>();

    public JdbcTypeJavaClassMapping() {
        initialize();
    }


    public String getType(Class<?> type) {
        Integer sqlType = javaClassToTypeMappingMap.get(type);

        if (sqlType == null) {
            throw new NotFoundSqlTypeException();
        }

        return sqlTypeToJavaClassMap.get(sqlType);
    }

    private void initialize() {
        sqlTypeToJavaClassMap.put(Types.INTEGER, "int");
        sqlTypeToJavaClassMap.put(Types.VARCHAR, "varchar");
        sqlTypeToJavaClassMap.put(Types.BIGINT, "bigint");
        sqlTypeToJavaClassMap.put(Types.BOOLEAN, "tinyint");


        javaClassToTypeMappingMap.put(Integer.class, Types.INTEGER);
        javaClassToTypeMappingMap.put(String.class, Types.VARCHAR);
        javaClassToTypeMappingMap.put(Long.class, Types.BIGINT);
        javaClassToTypeMappingMap.put(Boolean.class, Types.BOOLEAN);
        javaClassToTypeMappingMap.put(int.class, Types.INTEGER);
    }
}
