package persistence.sql.dml;

import java.lang.reflect.Field;

public class ColumnValue {

    private final Object value;

    public ColumnValue(Field field, Object value) {
        this.value = value;
        new ValidateInsertValue(field, value);
    }

    public String toSqlValue() {
        return (value != null) ? "'" + value + "'" : "NULL";
    }

}
