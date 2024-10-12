package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public record EntityField(String name, Class<?> type, boolean nullable, int length) {
    public static EntityField from(Field field) {
        return new EntityField(
                getName(field),
                field.getType(),
                isNullable(field),
                getLength(field)
        );
    }

    private static String getName(Field field) {
        Column column = field.getAnnotation(Column.class);

        if (column == null || column.name().isEmpty()) {
            return field.getName();
        }

        return column.name();
    }

    private static boolean isNullable(Field field) {
        Column column = field.getAnnotation(Column.class);

        if (column == null) {
            return true;
        }

        return column.nullable();
    }

    private static int getLength(Field field) {
        Column column = field.getAnnotation(Column.class);

        if (column == null) {
            return 255;
        }

        return column.length();
    }
}
