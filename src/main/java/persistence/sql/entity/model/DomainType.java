package persistence.sql.entity.model;

import jakarta.persistence.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class DomainType {
    private static final String EMPTY = "";

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

    public String getColumnName() {
        Column columnAnnotation = this.getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        }
        return this.getName();
    }

    public static DomainType from(Field field) {
        return new DomainType(
                field.getName(),
                field.getType(),
                field,
                field.isAnnotationPresent(Transient.class)
        );
    }

    public boolean isNotExistsId() {
        return !this.isAnnotation(Id.class);
    }

    public boolean isNotExistGenerateValue() {
        return !this.isAnnotation(GeneratedValue.class);
    }

    public boolean isExistsIdentity() {
        return this.getAnnotation(GeneratedValue.class).strategy() != GenerationType.IDENTITY;
    }

    public boolean isColumnAnnotation() {
        return this.isAnnotation(Column.class);
    }
}
