package common;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Predicate;

public class AnnotationValidation {
    public AnnotationValidation() {
    }

    public static boolean isPresent(Class<?> clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    public static boolean isNotPresent(Class<?> clazz, Class<? extends Annotation> annotation) {
        return !isPresent(clazz, annotation);
    }

    public static boolean isPresent(Field field, Class<? extends Annotation> annotation) {
        return field.isAnnotationPresent(annotation);
    }

    public static boolean isNotPresent(Field field, Class<? extends Annotation> annotation) {
        return !isPresent(field, annotation);
    }

    public static boolean isNotBlank(String name) {
        return !name.isBlank();
    }

    @NotNull
    public static Predicate<Field> notPredicate(Class<? extends Annotation> annotationClass) {
        return field -> isNotPresent(field, annotationClass);
    }

    @NotNull
    public static Predicate<Field> notIdentifier() {
        return field -> isNotPresent(field, Id.class)
                && isNotPresent(field, GeneratedValue.class);
    }

}
