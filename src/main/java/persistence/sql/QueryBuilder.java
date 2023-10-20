package persistence.sql;

import jakarta.persistence.Entity;

public interface QueryBuilder {

    default void validateEntityClass(Class<?> objectClass) {
        if (!objectClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("@Entity 애노테이션이 붙은 클래스에 대해서만 쿼리를 수행할 수 있습니다.");
        }
    }

    String getQuery();

}
