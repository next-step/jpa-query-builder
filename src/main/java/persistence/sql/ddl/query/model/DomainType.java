package persistence.sql.ddl.query.model;

import jakarta.persistence.Transient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class DomainType {

    private final String name;
    private final Class<?> classType;
    private final Field field;
    private final boolean isTransient;

    public DomainType(final String name,
                      final Class<?> classType,
                      final Field field,
                      final boolean isTransient) {
        this.name = name;
        this.classType = classType;
        this.field = field;
        this.isTransient = isTransient;
    }

    public static DomainType from(Field field) {
        return new DomainType(
                field.getName(),
                field.getType(),
                field,
                field.isAnnotationPresent(Transient.class)
        );
    }

    public String getName() {
        return name;
    }

    public Class<?> getClassType() {
        return classType;
    }

    public boolean isNotTransient() {
        return !isTransient;
    }

    public boolean isAnnotation(Class<? extends Annotation> annotation) {
        return field.isAnnotationPresent(annotation);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotation) {
        return field.getAnnotation(annotation);
    }

}
