package persistence.sql.ddl;

import java.util.Map;

// NOTE: https://github.com/hibernate/hibernate-orm/blob/main/hibernate-core/src/main/java/org/hibernate/type/descriptor/jdbc/JdbcTypeJavaClassMappings.java
public final class TypeMapper {
    private static final Map<Class<?>, String> javaToSql = Map.of(
            Long.class, "BIGINT",
            Integer.class, "BIGINT",
            String.class, "TEXT"
    );

    private TypeMapper() {}

    public static String toSqlType(Class<?> javaType) {
        return javaToSql.get(javaType);
    }
}
