package hibernate.entity;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class EntityId {

    public EntityId(final Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            throw new IllegalArgumentException("Id 어노테이션이 없습니다.");
        }
    }
}
