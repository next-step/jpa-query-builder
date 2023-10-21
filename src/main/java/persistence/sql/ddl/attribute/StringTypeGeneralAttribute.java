package persistence.sql.ddl.attribute;

import jakarta.persistence.Column;
import persistence.sql.ddl.converter.SqlConverter;

import java.lang.reflect.Field;


public class StringTypeGeneralAttribute extends GeneralAttribute {
    private final int length;
    private final String fieldName;
    private final String columnName;
    private final boolean nullable;


    private StringTypeGeneralAttribute(int length, String fieldName, String columnName, boolean nullable, SqlConverter sqlConverter) {
        super(sqlConverter);
        this.length = length;
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.nullable = nullable;
    }

    public static StringTypeGeneralAttribute of(Field field, SqlConverter sqlConverter) {
        Column column = field.getDeclaredAnnotation(Column.class);
        return new StringTypeGeneralAttribute(
                column.length(),
                field.getName(),
                column.name().isBlank() ? field.getName() : column.name(),
                column.nullable(),
                sqlConverter
        );
    }

    @Override
    public String prepareDDL() {
        String component = (columnName.isBlank() ? fieldName : columnName) + " " +
                sqlConverter.convert(String.class) +
                String.format("(%s)", length) + (!nullable ? " NOT NULL" : "");
        return component.trim();
    }

    @Override
    public String getColumnName() {
        return this.columnName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }
}
