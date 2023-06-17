package persistence.sql.dialect.h2;

import persistence.sql.dialect.TypeDialect;

import java.util.Map;

import static persistence.sql.util.StringConstant.BLANK;

// NOTE: https://github.com/hibernate/hibernate-orm/blob/main/hibernate-core/src/main/java/org/hibernate/type/descriptor/jdbc/JdbcTypeJavaClassMappings.java
public final class H2TypeDialect implements TypeDialect {
    private static final Map<Class<?>, String> javaToSql = Map.of(
            Long.class, "BIGINT",
            Integer.class, "INTEGER",
            String.class, "VARCHAR"
    );

    private H2TypeDialect() {}

    public static H2TypeDialect getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public String getSqlType(Class<?> javaType) {
        return javaToSql.getOrDefault(javaType, BLANK);
    }

    private static class SingletonHelper {
        private static final H2TypeDialect INSTANCE = new H2TypeDialect();
    }
}
