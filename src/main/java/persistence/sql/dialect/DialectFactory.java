package persistence.sql.dialect;

import database.DatabaseServer;
import database.H2;
import persistence.sql.dialect.type.DataTypeRegistry;
import persistence.sql.dialect.type.H2DataTypeMappingStrategy;

public class DialectFactory {
    public static Dialect create(Class<? extends DatabaseServer> databaseServerClass) {
        if (databaseServerClass.equals(H2.class)) {
            return new H2Dialect(new DataTypeRegistry(new H2DataTypeMappingStrategy()));
        }
        throw new IllegalArgumentException("NOT SUPPORTED DATABASE. name = " + databaseServerClass.getSimpleName());
    }
}
