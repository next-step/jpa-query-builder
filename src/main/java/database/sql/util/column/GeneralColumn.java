package database.sql.util.column;

import database.sql.util.type.TypeConverter;

public class GeneralColumn implements IColumn {
    private final String columnName;
    private final Class<?> type;
    private final Integer columnLength;
    private final boolean nullable;

    public GeneralColumn(String columnName,
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
        return columnName + " " + typeConverter.convert(type, columnLength) + " " + nullableOrNot();
    }

    @Override
    public boolean isPrimaryKeyField() {
        return false;
    }

    private String nullableOrNot() {
        return nullable ? "NULL" : "NOT NULL";
    }
}
