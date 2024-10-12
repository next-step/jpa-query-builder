package persistence.sql.ddl.impl;

import persistence.sql.ddl.Dialect;

import java.sql.Types;

public class H2Dialect extends Dialect {

    public static H2Dialect create() {
        H2Dialect dialect = new H2Dialect();

        dialect.registerColumnType(Types.INTEGER, "INTEGER");
        dialect.registerColumnType(Types.VARCHAR, "VARCHAR");
        dialect.registerColumnType(Types.BIGINT, "BIGINT");

        return dialect;
    }
}
