package persistence.sql.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class ColumnNullable {

    private static final ColumnNullable NOT_NULLABLE = new ColumnNullable(false);
    private static final ColumnNullable NULLABLE = new ColumnNullable(true);

    private final boolean nullable;

    private ColumnNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public static ColumnNullable getInstance(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (isNotNullable(field, column)) {
            return NOT_NULLABLE;
        }
        return NULLABLE;
    }

    private static boolean isNotNullable(Field field, Column column) {
        return field.isAnnotationPresent(Id.class) || column != null && !column.nullable();
    }

    public boolean isNullable() {
        return this.nullable;
    }
}
