package hibernate;

import jakarta.persistence.Entity;

public class EntityClass {

    public EntityClass(final Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 어노테이션이 없는 클래스는 입력될 수 없습니다.");
        }
    }
}
