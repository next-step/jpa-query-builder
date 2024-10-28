package persistence.metadata;

import java.lang.reflect.Field;

public record FieldValue(ColumnName columnName,
                         Object columnValue,
                         Class<?> type) {

    public FieldValue(Object object, Field field) {
        this(
                new ColumnName(field),
                getFieldValue(object, field),
                field.getType()
        );
    }

    private static Object getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } finally {
            field.setAccessible(false);
        }
    }

    public String fieldValueHandleToString() {
        if (ColumnType.isVarcharType(this.columnValue.getClass())) {
            return "'" + this.columnValue + "'";
        }

        return this.columnValue.toString();
    }
}
