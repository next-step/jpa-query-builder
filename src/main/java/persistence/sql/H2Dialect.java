package persistence.sql;

import java.sql.Types;

public class H2Dialect extends Dialect {

    public H2Dialect() {
        registerColumnType(Types.INTEGER, "INTEGER");
        registerColumnType(Types.BIGINT, "BIGINT");
        registerColumnType(Types.VARCHAR, "VARCHAR(255)");
    }
}
