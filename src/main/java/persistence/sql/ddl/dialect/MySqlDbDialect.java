package persistence.sql.ddl.dialect;

import persistence.sql.ddl.dialect.type.JavaToMySqlType;

import java.util.Map;

public class MySqlDbDialect implements Dialect {
    private static final Map<Class<?>, JavaToMySqlType> JAVA_TO_MYSQL_TYPE_MAP = Map.of(
            Long.class, JavaToMySqlType.LONG,
            String.class, JavaToMySqlType.STRING,
            Integer.class, JavaToMySqlType.INTEGER
    );

    @Override
    public boolean support(String dbType) {
        return dbType.equals("mysql");
    }

    @Override
    public String getType(Class<?> type) {
        final JavaToMySqlType javaToMySqlType = JAVA_TO_MYSQL_TYPE_MAP.get(type);
        if (javaToMySqlType == null) {
            throw new IllegalStateException("not found valid dbType for javaType: " + type.getTypeName());
        }
        return javaToMySqlType.getDbType();
    }
}
