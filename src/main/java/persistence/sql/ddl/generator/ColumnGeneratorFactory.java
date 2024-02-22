package persistence.sql.ddl.generator;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;

import java.lang.reflect.Field;

public class ColumnGeneratorFactory {
    public static ColumnGenerator getColumnGenerator(final Dialect dialect, final Field[] fields) {
        if (dialect instanceof H2Dialect) {
            return new H2ColumnGenerator(dialect, fields);
        }
        throw new IllegalArgumentException("Unsupported Dialect type " + dialect.getClass());
    }
}
