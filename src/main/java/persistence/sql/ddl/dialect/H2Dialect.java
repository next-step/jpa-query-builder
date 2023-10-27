package persistence.sql.ddl.dialect;

import java.sql.Types;

public class H2Dialect extends Dialect {

    public H2Dialect() {
        registerColumnType(Types.VARCHAR, "VARCHAR");
        registerColumnType(Types.INTEGER, "INT");
        registerColumnType(Types.BIGINT, "BIGINT");
    }

}
