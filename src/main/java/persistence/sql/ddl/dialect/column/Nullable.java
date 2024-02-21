package persistence.sql.ddl.dialect.column;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public enum Nullable {
    NULL,
    NOT_NULL;

    public static Nullable getNullable(final Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return NOT_NULL;
        }
        if (!field.isAnnotationPresent(Column.class)) {
            return NULL;
        }
        if (field.getAnnotation(Column.class).nullable()) {
            return NULL;
        }
        return NOT_NULL;
    }
}
