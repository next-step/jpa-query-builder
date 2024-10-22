package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public record EntityColumn(Field field, String name, Class<?> type, boolean nullable, int length) {
    public static EntityColumn from(Field field) {
        Column column = field.getAnnotation(Column.class);

        return new EntityColumn(
            field,
            getName(field, column),
            field.getType(),
            isNullable(column),
            getLength(column)
        );
    }

    private static String getName(Field field, Column column) {
        if (column == null || column.name().isEmpty()) {
            return field.getName();
        }

        return column.name();
    }

    private static boolean isNullable(Column column) {
        if (column == null) {
            return true;
        }

        return column.nullable();
    }

    private static int getLength(Column column) {
        if (column == null) {
            return (int) AnnotationUtils.getDefaultValue(Column.class, "length");
        }

        return column.length();
    }

    public boolean isEqualName(String name) {
        return this.name.equals(name);
    }

    public Object getValue(Object entity) {
        return FieldUtils.getValue(this.field, entity);
    }

    public void applyValue(Object entity, Object value) {
        FieldUtils.setValue(this.field, entity, value);
    }
}
