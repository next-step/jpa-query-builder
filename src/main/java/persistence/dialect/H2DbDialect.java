package persistence.dialect;

import persistence.dialect.type.JavaToH2Type;

import java.util.Map;

public class H2DbDialect implements Dialect {
    private static final Map<Class<?>, JavaToH2Type> JAVA_TO_H2_TYPE_MAP = Map.of(
            Long.class, JavaToH2Type.LONG,
            String.class, JavaToH2Type.STRING,
            Integer.class, JavaToH2Type.INTEGER
    );

    @Override
    public boolean support(String dbType) {
        return dbType.equals("h2");
    }

    @Override
    public String getType(Class<?> type) {
        final JavaToH2Type javaToH2Type = JAVA_TO_H2_TYPE_MAP.get(type);
        if (javaToH2Type == null) {
            throw new IllegalStateException("not found valid dbType for javaType: " + type.getTypeName());
        }
        return javaToH2Type.getDbType();
    }
}
