package persistence.sql.extract;

import java.lang.annotation.Annotation;

public interface AnnotationPropertyExtractor {


    static boolean isPresent(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return clazz.isAnnotationPresent(annotationClass);
    }

    static boolean isNotPresent(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return !isPresent(clazz, annotationClass);
    }

    static boolean isNotBlank(String str) {
        return !str.isBlank();
    }

}
