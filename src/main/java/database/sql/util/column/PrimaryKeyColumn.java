package database.sql.util.column;

import database.sql.util.type.TypeConverter;

public class PrimaryKeyColumn implements Column {
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
        StringBuilder definitionBuilder = new StringBuilder();
        definitionBuilder.append(columnName).append(" ");
        definitionBuilder.append(typeConverter.convert(type, columnLength)).append(" ");
        if (autoIncrement) {
            definitionBuilder.append("AUTO_INCREMENT").append(" ");
        }
        definitionBuilder.append("PRIMARY KEY");
        return definitionBuilder.toString();
    }

    @Override
    public boolean isPrimaryKeyField() {
        return true;
    }
}
