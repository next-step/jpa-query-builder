package hibernate;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class EntityField {

    private final String fieldName;
    private final boolean isNullable;

    public EntityField(final Field field) {
        this.fieldName = parseFieldName(field);
        this.isNullable = false;
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

    public String getFieldName() {
        return fieldName;
    }

    public boolean isNullable() {
        return isNullable;
    }
}
