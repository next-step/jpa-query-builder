package utils;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

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

    public static Stream<Field> getNonTransientData(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class));
    }

}
