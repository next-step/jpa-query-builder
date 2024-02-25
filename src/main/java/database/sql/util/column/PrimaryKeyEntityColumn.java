package database.sql.util.column;

import database.sql.util.type.TypeConverter;

import java.lang.reflect.Field;
import java.util.StringJoiner;

public class PrimaryKeyEntityColumn extends AbstractEntityColumn {
    private final boolean autoIncrement;

    // field 생성 후 주입이 어려워서 테스트가 쉽지 않다
    public PrimaryKeyEntityColumn(Field field,
                                  String columnName,
                                  Class<?> type,
                                  Integer columnLength,
                                  boolean autoIncrement) {
        super(field, columnName, type, columnLength);

        this.autoIncrement = autoIncrement;
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
