package persistence.sql.ddl.utils;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class Nullable {
    private static final String NULL = "NULL";
    private static final String NOT_NULL = "NOT NULL";
    private final Field field;

    public Nullable(Field field) {
        this.field = field;
    }

    public String getQuery() {
        boolean isNullable = field.getAnnotation(Column.class).nullable();
        if (isNullable) {
            return NULL;
        }
        return NOT_NULL;
    }
}
