package database.sql.util.column;

import database.sql.util.type.TypeConverter;

public class GeneralEntityColumn implements EntityColumn {
    private final String columnName;
    private final Class<?> type;
    private final Integer columnLength;
    private final boolean nullable;

    public GeneralEntityColumn(String columnName,
                               Class<?> type,
                               Integer columnLength,
                               boolean nullable) {
        this.columnName = columnName;
        this.type = type;
        this.columnLength = columnLength;
        this.nullable = nullable;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public String toColumnDefinition(TypeConverter typeConverter) {
        return columnName + " " + typeConverter.convert(type, columnLength) + " " + toNullableDefinition();
    }

    @Override
    public boolean isPrimaryKeyField() {
        return false;
    }

    private String toNullableDefinition() {
        return nullable ? "NULL" : "NOT NULL";
    }
}
