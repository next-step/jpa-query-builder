package persistence.sql.ddl.utils;

import persistence.sql.ddl.type.DataType;

public interface ColumnType {

    String getName();

    boolean isId();

    boolean isNullable();

    boolean isTransient();

    int getLength();

    DataType getDataType();

}
