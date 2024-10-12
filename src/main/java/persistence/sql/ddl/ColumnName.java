package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public record ColumnName(
        String name
) {
    public static ColumnName from(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String name = field.getDeclaredAnnotation(Column.class).name();
            if (!name.isEmpty()) {
                return new ColumnName(name);
            }
        }

        return new ColumnName(field.getName());
    }
}
