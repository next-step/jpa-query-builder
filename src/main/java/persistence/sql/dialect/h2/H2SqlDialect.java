package persistence.sql.dialect.h2;

import persistence.sql.ddl.DdlBuilder;
import persistence.sql.ddl.h2.H2DdlBuilder;
import persistence.sql.dialect.SqlDialect;
import persistence.sql.dml.DmlBuilder;
import persistence.sql.dml.h2.H2DmlBuilder;

public final class H2SqlDialect implements SqlDialect {
    private H2SqlDialect() {}

    public static H2SqlDialect getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public DdlBuilder getDdl() {
        return H2DdlBuilder.getInstance();
    }

    @Override
    public DmlBuilder getDml() {
        return H2DmlBuilder.getInstance();
    }


    private static class SingletonHelper {
        private static final H2SqlDialect INSTANCE = new H2SqlDialect();
    }
}
