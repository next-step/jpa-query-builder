package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Arrays;

public class DDLQueryValidator {

    public void validate(Class<?> tClass) {
        validateEntityAnnotation(tClass);
        validateIdAnnotation(tClass);
    }

    private void validateEntityAnnotation(Class<?> tClass) {
        if (!tClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(String.format("[%s] 엔티티 어노테이션이 없습니다.", tClass.getName()));
        }
    }

    private void validateIdAnnotation(Class<?> tClass) {
        long count = Arrays.stream(tClass.getDeclaredFields()).filter(
                field -> field.isAnnotationPresent(Id.class)
        ).count();

        if (count != 1) {
            throw new IllegalArgumentException(String.format("[%s] @Id 어노테이션이 정확히 1개 존재해야합니다.", tClass.getName()));
        }
    }
}
