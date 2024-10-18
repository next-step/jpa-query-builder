package persistence.sql.ddl;

import jakarta.persistence.Entity;

public class ValidateEntity {

    public ValidateEntity(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(
                "Provided class is not an entity: " +
                    entityClass.getName()
            );
        }
    }

}
