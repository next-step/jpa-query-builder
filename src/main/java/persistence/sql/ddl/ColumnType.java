package persistence.sql.ddl;

import java.lang.reflect.Field;

public class ColumnType {

    private final Field field;

    public ColumnType(Field field) {
        this.field = field;
    }

    public String getColumnType() {
        Class<?> fieldType = field.getType();
        String columnType = new SqlType(fieldType).getSqlType();

        ColumnConstraints columnConstraints = new ColumnConstraints(field);
        columnType += columnConstraints.getColumnConstraints();

        return columnType;
    }

}
