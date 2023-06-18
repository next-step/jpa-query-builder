package persistence.sql.dialect;

import persistence.sql.ddl.DdlBuilder;
import persistence.sql.dml.DmlBuilder;

public interface SqlDialect {
    DdlBuilder getDdl();

    DmlBuilder getDml();
}
