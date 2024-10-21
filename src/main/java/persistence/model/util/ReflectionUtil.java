package persistence.model.util;

import jakarta.persistence.Column;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

public class ReflectionUtil {
    public static <T extends Annotation> Optional<T> getAnnotationIfPresent(Field field, Class<T> annotationClass) {
        if (field.isAnnotationPresent(annotationClass)) {
            return Optional.ofNullable(field.getAnnotation(annotationClass));
        }
        return Optional.empty();
    }

    public static <T extends Annotation> Optional<T> getAnnotationIfPresent(Class<?> clazz, Class<T> annotationClass) {
        if (clazz.isAnnotationPresent(annotationClass)) {
            return Optional.ofNullable(clazz.getAnnotation(annotationClass));
        }
        return Optional.empty();
    }

    public static String getColumnName(Field field) {
        return getAnnotationIfPresent(field, Column.class)
                .map(Column::name)
                .filter(name -> !name.isEmpty())
                .orElse(field.getName());
    }
}
