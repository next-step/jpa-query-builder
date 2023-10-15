package persistence.sql.meta;

import jakarta.persistence.Entity;

public class EntityMeta {

    public static boolean isEntity(Class<?> clazz) {
        return clazz.getDeclaredAnnotation(Entity.class) != null;
    }
}
