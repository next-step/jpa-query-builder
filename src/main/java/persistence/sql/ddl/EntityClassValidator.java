package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassValidator {
    private EntityClassValidator() {
    }

    public static void validate(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity must be annotated with @Entity");
        }

        validateHasId(clazz.getDeclaredFields());
    }

    private static void validateHasId(Field[] fields) {
        List<Field> idFields = Arrays.stream(fields)
                .filter(field ->
                        field.isAnnotationPresent(Id.class)
                ).toList();

        if (idFields.size() != 1) {
            throw new IllegalArgumentException("Entity must have exactly one field annotated with @Id");
        }
    }
}
