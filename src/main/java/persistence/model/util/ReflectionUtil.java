package persistence.model.util;

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
}
