package persistence.sql.ddl.utils;

import persistence.sql.ddl.type.DataType;

public interface ColumnType {

    String getName();

    boolean isId();

    boolean isNullable();

    boolean isTransient();

    String getLength();

    DataType getDataType();

}
