package hibernate;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class EntityField {

    private final String fieldName;
    private final boolean isNullable;

    public EntityField(final Field field) {
        this.fieldName = parseFieldName(field);
        this.isNullable = parseNullable(field);
    }

    private String parseFieldName(final Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }
        String fieldName = field.getAnnotation(Column.class).name();
        if (fieldName.isEmpty()) {
            return field.getName();
        }
        return fieldName;
    }

    private boolean parseNullable(final Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return true;
        }
        return field.getAnnotation(Column.class).nullable();
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isNullable() {
        return isNullable;
    }
}
