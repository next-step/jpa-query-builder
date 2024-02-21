package persistence.sql.ddl.h2;

import persistence.sql.Dialect;
import persistence.sql.ddl.h2.meta.Nullable;

import java.sql.Types;

public class H2Dialect extends Dialect {
    @Override
    protected void setDefaultColumnDataTypes() {
        registerColumnDataType(Types.VARCHAR, "varchar(255)");
        registerColumnDataType(Types.INTEGER, "int");
        registerColumnDataType(Types.BIGINT, "bigint");
    }

    @Override
    protected void setDefaultNullableTypes() {
        registerColumnNullableTypes(Nullable.NULL, "NULL");
        registerColumnNullableTypes(Nullable.NOT_NULL, "NOT NULL");
    }
}
