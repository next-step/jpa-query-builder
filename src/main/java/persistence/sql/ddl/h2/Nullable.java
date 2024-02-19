package persistence.sql.ddl.h2;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public enum Nullable {
    NULL("NULL"),
    NOT_NULL("NOT NULL");

    private final String sql;

    Nullable(final String sql) {
        this.sql = sql;
    }

    public static String getSQL(final Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return NOT_NULL.sql;
        }
        if (!field.isAnnotationPresent(Column.class)) {
            return NULL.sql;
        }
        if (field.getAnnotation(Column.class).nullable()) {
            return NULL.sql;
        };
        return NOT_NULL.sql;
    }
}
