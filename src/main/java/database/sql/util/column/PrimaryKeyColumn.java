package database.sql.util.column;

import database.sql.util.type.TypeConverter;

import java.util.StringJoiner;

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
        StringJoiner definitionJoiner = new StringJoiner(" ");
        definitionJoiner.add(columnName);
        definitionJoiner.add(typeConverter.convert(type, columnLength));
        if (autoIncrement) {
            definitionJoiner.add("AUTO_INCREMENT");
        }
        definitionJoiner.add("PRIMARY KEY");
        return definitionJoiner.toString();
    }

    @Override
    public boolean isPrimaryKeyField() {
        return true;
    }
}
