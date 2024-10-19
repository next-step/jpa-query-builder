package persistence.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AnnotationValidator {

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

    public static boolean isNotBlank(String str) {
        return !str.isBlank();
    }

}
