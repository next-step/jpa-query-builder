package persistence.sql.dialect.h2;

import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.TypeDialect;

public class H2Dialect implements Dialect {

    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";

    private static final H2Dialect INSTANCE = new H2Dialect();

    private H2Dialect() {}

    public static H2Dialect getInstance() {
        return INSTANCE;
    }

    @Override
    public TypeDialect getTypeDialect() {
        return H2TypeDialect.getInstance();
    }

    @Override
    public String getGenerationTypeIdentity() {
        return AUTO_INCREMENT;
    }
}
