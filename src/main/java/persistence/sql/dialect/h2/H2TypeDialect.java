package persistence.sql.dialect.h2;

import persistence.sql.dialect.TypeDialect;

public class H2TypeDialect extends TypeDialect {

    private static final H2TypeDialect INSTANCE = new H2TypeDialect();

    private H2TypeDialect() {
        registerType(Long.class, "BIGINT");
        registerType(Integer.class, "INT");
        registerType(String.class, "VARCHAR");
    }

    public static H2TypeDialect getInstance() {
        return INSTANCE;
    }
}
