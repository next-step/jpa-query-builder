package persistence.inspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClassInspector<T> {

    public final Class<T> clazz;

    public ClassInspector(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getClassName() {
        return clazz.getSimpleName();
    }

    public List<Field> getFields() {
        return Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> target) {
        return clazz.isAnnotationPresent(target);
    }

    public <A extends Annotation> A getAnnotation(Class<A> target) {
        return clazz.getAnnotation(target);
    }
}
