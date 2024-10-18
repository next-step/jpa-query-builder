package persistence.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface AnnotationValidator {


    static boolean isPresent(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return clazz.isAnnotationPresent(annotationClass);
    }

    static boolean isPresent(Field field, Class<? extends Annotation> annotationClass) {
        return field.isAnnotationPresent(annotationClass);
    }

    static boolean isNotPresent(Field field, Class<? extends Annotation> annotationClass) {
        return !isPresent(field, annotationClass);
    }


    static boolean isNotBlank(String str) {
        return !str.isBlank();
    }

}
