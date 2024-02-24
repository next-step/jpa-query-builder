package persistence.sql;

import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.DialectResolution;
import persistence.sql.dialect.database.Database;

public class StandardDialectResolver {

    private StandardDialectResolver() {}

    public static Dialect resolveDialect(final DialectResolution dialectResolution) {
        return Database.from(dialectResolution)
                .getDialectSupplier()
                .get();
    }

}
