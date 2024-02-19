package persistence.sql.ddl.h2;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnName {
    private final Field field;

    public ColumnName(final Field field) {
        this.field = field;
    }

    public String getColumnName() {
        if (hasColumnAnnotation()) {
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

    private boolean hasColumnAnnotation() {
        return field.isAnnotationPresent(Column.class);
    }

}
