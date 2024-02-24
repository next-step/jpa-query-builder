package database.sql.util.column;

import database.sql.util.type.TypeConverter;

import java.lang.reflect.Field;

public class GeneralEntityColumn extends AbstractEntityColumn {
    private final boolean nullable;

    public GeneralEntityColumn(Field field,
                               String columnName,
                               Class<?> type,
                               Integer columnLength,
                               boolean nullable) {
        super(field, columnName, type, columnLength);

        this.nullable = nullable;
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
