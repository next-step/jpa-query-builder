package hibernate.entity.column;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class EntityColumnFactory {

    public static boolean isAvailableCreateEntityColumn(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    public static EntityColumn create(final Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return new EntityId(field);
        }
        return new EntityField(field);
    }
}
