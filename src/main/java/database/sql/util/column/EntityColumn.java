package database.sql.util.column;

import database.sql.util.type.TypeConverter;

public interface EntityColumn {
    String getColumnName();

    String toColumnDefinition(TypeConverter typeConverter);

    boolean isPrimaryKeyField();

    String getFieldName();

    Object getValue(Object entity) throws IllegalAccessException;
}
