package hibernate.entity;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class EntityColumnFactory {

    public static EntityColumn create(final Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return new EntityId(field);
        }
        return new EntityField(field);
    }
}
