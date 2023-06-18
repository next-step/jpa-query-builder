package persistence.sql.util;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public final class ColumnName {

    private ColumnName() {}

    public static String build(Field field) {
        final Column column = field.getDeclaredAnnotation(Column.class);
        return column == null || column.name().isBlank()
                ? field.getName()
                : column.name();
    }
}
