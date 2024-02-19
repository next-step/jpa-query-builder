package persistence.sql.ddl.dialect.database;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.DialectResolution;
import persistence.sql.ddl.dialect.exception.NotFoundDatabase;
import persistence.sql.ddl.dialect.h2.H2Dialect;

import java.util.Arrays;

public enum Database {

    H2 {
        @Override
        public Dialect createDialect() {
            return new H2Dialect();
        }

        @Override
        public boolean productNameMatchers(String databaseName) {
            return "H2".equals(databaseName);
        }
    };

    public abstract Dialect createDialect();

    public abstract boolean productNameMatchers(String databaseName);


    public static Database from(final DialectResolution dialectResolution) {
        return Arrays.stream(values())
                .filter(value -> value.productNameMatchers(dialectResolution.getDatabaseName()))
                .findFirst()
                .orElseThrow(NotFoundDatabase::new);
    }

}
