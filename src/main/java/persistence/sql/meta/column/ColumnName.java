package persistence.sql.meta.column;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnName {
    private final String name;

    public ColumnName(final Field field) {
        this.name = extractName(field);
    }

    public String getName() {
        return name;
    }

    private String extractName(Field field) {
        if (hasColumnAnnotation(field)) {
            return extractNameByAnnotation(field);
        }
        return extractNameByField(field);
    }

    private String extractNameByAnnotation(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation.name().isEmpty()) {
            return extractNameByField(field);
        }
        return annotation.name();
    }

    private String extractNameByField(Field field) {
        return field.getName();
    }

    private boolean hasColumnAnnotation(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

}
