package persistence.sql.dialect.h2;

import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.TypeDialect;

public class H2Dialect implements Dialect {

    private static final String AUTO_INCREMENT = "AUTO_INCREMENT";

    @Override
    public TypeDialect getTypeDialect() {
        return H2TypeDialect.getInstance();
    }

    @Override
    public String getGenerationTypeIdentity() {
        return AUTO_INCREMENT;
    }
}
