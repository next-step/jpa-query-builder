package persistence.sql.ddl.column;

import java.lang.reflect.Field;


public class Column {

    private final ColumnName name;
    private final ColumnType type;
    private final ColumnLength length;
    private final boolean nullable;

    private Column(ColumnName name, ColumnType type, ColumnLength length, boolean nullable) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.nullable = nullable;
    }

    public static Column from(Field field) {
        jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);

        ColumnName name = ColumnName.from(field);
        ColumnType type = ColumnType.findColumnType(field.getType());
        ColumnLength length = ColumnLength.from(field);
        boolean nullable = column != null && column.nullable();

        return new Column(name, type, length, nullable);
    }

    public String getLengthDefinition() {
        if (type != ColumnType.STRING) {
            return "";
        }

        return String.format("(%d)", length.getLength());
    }

    public String getNullableDefinition() {
        if (nullable) {
            return "null";
        }

        return "not null";
    }

    public String getName() {
        return name.getName();
    }

    public String getType() {
        return type.getMysqlColumnType();
    }
}
