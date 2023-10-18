package hibernate.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class EntityId {

    private final GenerationType generationType;

    public EntityId(final Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            throw new IllegalArgumentException("Id 어노테이션이 없습니다.");
        }
        this.generationType = parseGenerationType(field);
    }

    private static GenerationType parseGenerationType(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return GenerationType.AUTO;
        }
        return field.getAnnotation(GeneratedValue.class).strategy();
    }

    public GenerationType getGenerationType() {
        return generationType;
    }
}
