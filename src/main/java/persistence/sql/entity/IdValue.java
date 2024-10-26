package persistence.sql.entity;

import jakarta.persistence.Id;
import java.lang.reflect.Field;

public class IdValue {
    private final Object entity;

    public IdValue(final Object entity) {
        this.entity = entity;
    }

    public Long value() {
        try {
            final Field[] fields = entity.getClass().getDeclaredFields();
            for (final Field field : fields) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    return (Long) field.get(entity);
                }
            }
            throw new IllegalArgumentException("No @Id field found in entity");
        } catch (final IllegalAccessException e) {
            throw new RuntimeException("Failed to get id value", e);
        }
    }
}
