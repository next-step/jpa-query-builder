package persistence.sql.dialet.h2;

import persistence.sql.dialet.Dialect;

public class H2Dialect implements Dialect {

    private static H2Dialect instance = null;
    private static final String AUTO_INCREMENT_DEFINITION = "AUTO_INCREMENT";


    private H2Dialect() {
    }

    public static synchronized H2Dialect getInstance() {
        if (instance == null) {
            instance = new H2Dialect();
        }
        return instance;
    }

    @Override
    public String getAutoIncrementDefinition() {
        return AUTO_INCREMENT_DEFINITION;
    }

    @Override
    public String getSqlTypeDefinition(Class<?> clazz) {
        return H2TypeDialect.getDialect(clazz).getSqlType();
    }
}
