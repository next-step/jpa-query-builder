package persistence.sql.ddl.collection;

import persistence.sql.ddl.DbDialect;

import java.util.Map;

import static persistence.sql.ddl.DbDialect.*;
import static persistence.sql.ddl.DbDialect.INTEGER;

public class DbDialectMap {
    private final Map<Class<?>, DbDialect> JAVA_TO_SQL;

    public DbDialectMap() {
        JAVA_TO_SQL = Map.of(
                Long.class, LONG,
                Long.TYPE, LONG,
                String.class, STRING,
                Integer.class, INTEGER,
                Integer.TYPE, INTEGER
        );
    }

    public DbDialect get(Class<?> type) {
        final DbDialect dbDialect = JAVA_TO_SQL.get(type);
        if (dbDialect == null) {
            throw new IllegalArgumentException("No dialect for type " + type);
        }
        return dbDialect;
    }
}
