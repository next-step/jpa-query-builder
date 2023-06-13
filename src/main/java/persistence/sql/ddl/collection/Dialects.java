package persistence.sql.ddl.collection;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2DbDialect;
import persistence.sql.ddl.dialect.MySqlDbDialect;

import java.util.List;

public class Dialects {
    private final List<Dialect> DIALECTS;

    public Dialects() {
        DIALECTS = List.of(new H2DbDialect(), new MySqlDbDialect());
    }

    public Dialect get(String dbType) {
        return DIALECTS.stream()
                .filter(dialect -> dialect.support(dbType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("not found valid dialect for dbType: " + dbType));
    }
}
