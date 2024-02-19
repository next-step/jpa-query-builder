package persistence.sql.dialet.h2;

import persistence.sql.dialet.Dialect;

public class H2Dialect implements Dialect {

    private static final String AUTO_INCREMENT_DEFINITION = "AUTO_INCREMENT";

    static {
        typeMap.put(String.class, "VARCHAR");
        typeMap.put(Integer.class, "INTEGER");
        typeMap.put(Long.class, "BIGINT");
    }

    private static class LazyHolder {
        private static final H2Dialect INSTANCE = new H2Dialect();
    }

    public static H2Dialect getInstance() {
        return LazyHolder.INSTANCE;
    }

    private H2Dialect() {
    }

    @Override
    public String getAutoIncrementDefinition() {
        return AUTO_INCREMENT_DEFINITION;
    }

    @Override
    public String getSqlTypeDefinition(Class<?> clazz) {
        if (!typeMap.containsKey(clazz)) {
            throw new IllegalArgumentException("존재하지 않은 타입니다.");
        }
        return typeMap.get(clazz);
    }
}
