package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class H2Column {
    private final Field field;

    public H2Column(final Field field) {
        this.field = field;
    }

    public String getColumnName() {
        if (field.isAnnotationPresent(Column.class)) {
            return getColumnNameByAnnotation();
        }
        return getColumnNameByField();
    }

    private String getColumnNameByAnnotation() {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation.name().isEmpty()) {
            return getColumnNameByField();
        }
        return annotation.name();
    }

    private String getColumnNameByField() {
        return field.getName();
    }
}
