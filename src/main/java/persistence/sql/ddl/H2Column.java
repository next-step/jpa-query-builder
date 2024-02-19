package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class H2Column {
    private final Field field;

    public H2Column(final Field field) {
        this.field = field;
    }

    public String getColumnName() {
        // TODO Refactor Depth
        if (field.isAnnotationPresent(Column.class)) {
            Column annotation = field.getAnnotation(Column.class);
            if (annotation.name().isEmpty()) {
                return field.getName();
            }
            return annotation.name();
        }
        return field.getName();
    }


}
