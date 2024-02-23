package persistence.sql.validator;

import jakarta.persistence.Entity;
import persistence.sql.exception.InvalidEntityException;

/**
 * entity가 아닐 경우 Exception throw
 */
public class EntityValidator {
    public static void validate(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new InvalidEntityException();
        }
    }
}
