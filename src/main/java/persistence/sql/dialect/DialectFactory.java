package persistence.sql.dialect;

import database.DatabaseServer;
import database.H2;
import persistence.sql.dialect.type.H2DataTypeRegistry;

public class DialectFactory {
    public static Dialect create(Class<? extends DatabaseServer> databaseServerClass) {
        if (databaseServerClass.equals(H2.class)) {
            return new H2Dialect(new H2DataTypeRegistry());
        }
        throw new IllegalArgumentException("NOT SUPPORTED DATABASE. name = " + databaseServerClass.getSimpleName());
    }
}
