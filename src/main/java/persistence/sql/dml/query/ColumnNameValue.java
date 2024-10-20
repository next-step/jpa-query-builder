package persistence.sql.dml.query;

import java.lang.reflect.Field;
import persistence.sql.ddl.type.ColumnType;
import persistence.sql.metadata.ColumnName;

public record ColumnNameValue(ColumnName columnName,
                              Object columnValue,
                              Class<?> type) {

    public ColumnNameValue(Object object, Field field) {
        this(
                new ColumnName(field),
                getColumnValue(object, field),
                field.getType()
        );
    }

    private static Object getColumnValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String columnValueString() {
        if (ColumnType.isVarcharType(columnValue.getClass())) {
            return "'" + columnValue + "'";
        }
        return columnValue.toString();
    }

}
