package persistence.sql.mapper;

import persistence.sql.ddl.type.DataType;

public interface ColumnType {

    String getName();

    Object getValue();

    boolean isId();

    boolean isNullable();

    boolean isTransient();

    String getLength();

    DataType getDataType();

}
