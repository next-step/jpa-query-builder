package persistence.metadata;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

import static common.AnnotationValidation.isNotBlank;
import static common.AnnotationValidation.isPresent;


public record ColumnName(String name) {

    public ColumnName(Field field) {
        this(getName(field));
    }

    private static String getName(Field field) {
        if (isColumnNamePresent(field)) {
            return getColumnName(field);
        }
        return getFieldName(field);
    }

    private static boolean isColumnNamePresent(Field field) {
        return isPresent(field, Column.class) && isNotBlank(getColumnName(field));
    }

    private static String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column.name();
    }
    private static String getFieldName(Field field) {
        return field.getName();
    }
}
