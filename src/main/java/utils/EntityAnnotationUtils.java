package utils;

import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.util.Optional;

public class EntityAnnotationUtils {

    public static Column getColumnAnnotation(final Field field) {
        return field.getAnnotation(Column.class);
    }

    public static String parseColumnName(Field field) {
        return Optional.ofNullable(getColumnAnnotation(field))
                .filter(column -> !column.name().isEmpty())
                .map(Column::name)
                .orElse(field.getName());
    }

}
