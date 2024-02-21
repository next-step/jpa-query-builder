package database.sql.util.column;

import database.sql.util.type.TypeConverter;

public interface Column {
    String getColumnName();

    String toColumnDefinition(TypeConverter typeConverter);

    boolean isPrimaryKeyField();
}
