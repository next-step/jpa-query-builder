package persistence.sql.ddl;

import jakarta.persistence.Entity;

public class QueryValidator {
    public QueryValidator() {
    }

    public void checkIsEntity(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 클래스가 아닙니다.");
        }
    }
}
