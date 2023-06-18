package persistence.sql.dialect;

import persistence.sql.ddl.DdlBuilder;
import persistence.sql.dialect.h2.H2SqlDialect;
import persistence.sql.dml.DmlBuilder;

public enum Dialect {
    H2(H2SqlDialect.getInstance());
    private final SqlDialect sqlDialect;

    Dialect(SqlDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    public DdlBuilder getDdl() {
        return sqlDialect.getDdl();
    }

    public DmlBuilder getDml() {
        return sqlDialect.getDml();
    }
}
