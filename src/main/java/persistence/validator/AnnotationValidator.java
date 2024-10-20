package persistence.validator;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

public class AnnotationValidator {

    private AnnotationValidator() {

    }

    public static boolean isPresent(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return clazz.isAnnotationPresent(annotationClass);
    }

    public static boolean isNotPresent(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return !isPresent(clazz, annotationClass);
    }

    public static boolean isPresent(Field field, Class<? extends Annotation> annotationClass) {
        return field.isAnnotationPresent(annotationClass);
    }

    public static boolean isNotPresent(Field field, Class<? extends Annotation> annotationClass) {
        return !isPresent(field, annotationClass);
    }

    @NotNull
    public static Predicate<Field> notIdentifier() {
        return field -> isNotPresent(field, Id.class)
                && isNotPresent(field, GeneratedValue.class);
    }

    @NotNull
    public static Predicate<Field> notPredicate(Class<? extends Annotation> annotationClass) {
        return field -> isNotPresent(field, annotationClass);
    }

    public static boolean isNotBlank(String str) {
        return !str.isBlank();
    }

}
