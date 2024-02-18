package persistence.sql.ddl.validator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Arrays;

public class EntityValidator {

    private static EntityValidator instance = null;

    private EntityValidator() {
    }

    public static synchronized EntityValidator getInstance() {
        if (instance == null) {
            instance = new EntityValidator();
        }
        return instance;
    }

    public void validate(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("엔티티 객체가 아닙니다.");
        }

        long idFieldCount = Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Id.class))
            .count();

        if (idFieldCount > 1) {
            throw new IllegalArgumentException("Id 필드는 한개만 가져야 합니다.");
        }
    }
}

