package persistence.sql.dialect;

import persistence.sql.dialect.column.Nullable;

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
