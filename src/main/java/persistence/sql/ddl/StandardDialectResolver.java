package persistence.sql.ddl;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.DialectResolution;
import persistence.sql.ddl.dialect.DialectResolutionInfo;
import persistence.sql.ddl.dialect.database.Database;

public class StandardDialectResolver {

    private StandardDialectResolver() {}

    public static Dialect resolveDialect(final DialectResolution dialectResolution) {
        return Database.from(dialectResolution)
                .getDialectSupplier()
                .get();
    }

}
