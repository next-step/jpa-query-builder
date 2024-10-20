package persistence.sql.dml;

import jakarta.persistence.Id;
import java.lang.reflect.Field;
import lombok.Getter;

@Getter
public class EntityId<T> {

    private final long id;

    public EntityId(T entity) {
        this.id = getIdValue(entity);
    }

    private long getIdValue(T entity) {
        try {
            for (Field field : entity.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    return (long) field.get(entity);
                }
            }
            throw new IllegalStateException("No @Id field found in entity: " + entity.getClass().getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access @Id field in entity", e);
        }
    }

}
