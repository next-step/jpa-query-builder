package database.sql.util.column;

import database.sql.util.type.TypeConverter;

public class PrimaryKeyColumn implements IColumn {
    private final String columnName;
    private final Class<?> type;
    private final Integer columnLength;
    private final boolean autoIncrement;

    public PrimaryKeyColumn(String columnName,
                            Class<?> type,
                            Integer columnLength,
                            boolean autoIncrement) {
        this.columnName = columnName;
        this.type = type;
        this.columnLength = columnLength;
        this.autoIncrement = autoIncrement;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public String toColumnDefinition(TypeConverter typeConverter) {
        return columnName + " " + typeConverter.convert(type, columnLength) + " " + withBlank(autoIncrement, "AUTO_INCREMENT") + "PRIMARY KEY";
    }

    @Override
    public boolean isPrimaryKeyField() {
        return true;
    }

    private String withBlank(boolean b, String sql) {
        if (b) return sql + " ";
        return "";
    }
}
