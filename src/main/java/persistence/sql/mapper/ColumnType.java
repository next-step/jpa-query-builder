package persistence.sql.mapper;

import persistence.sql.ddl.type.DataType;

public interface ColumnType {

    String getName();

    String getValue();

    boolean isId();

    boolean isNullable();

    boolean isTransient();

    String getLength();

    DataType getDataType();

}
