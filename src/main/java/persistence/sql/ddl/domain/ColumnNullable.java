package persistence.sql.ddl.domain;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnNullable {

    public static ColumnNullable NOT_NULLABLE = new ColumnNullable(false);
    public static ColumnNullable NULLABLE = new ColumnNullable(true);

    private final boolean nullable;

    private ColumnNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public static ColumnNullable getInstance(Field field) {
        Column column = field.getAnnotation(Column.class);
        return new ColumnNullable(column == null || column.nullable());
    }

    public boolean isNullable() {
        return this.nullable;
    }
}
