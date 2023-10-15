package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CreateQueryBuilder {

    public static String getQuery(Class<?> clazz) {

        // Domain 클래스 검증
        validateEntity(clazz);

        return "";
    }

    private static void validateEntity(Class<?> clazz) {

        validateEntityAnnotation(clazz);
        validatePkAnnotation(clazz);
    }

    private static void validateEntityAnnotation(Class<?> clazz) {
        Entity entityAnnotation = clazz.getDeclaredAnnotation(Entity.class);
        if (entityAnnotation == null) {
            throw new IllegalArgumentException("Create Query 빌드 대상이 아닙니다.");
        }
    }

    private static void validatePkAnnotation(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        boolean isPkPresent = Arrays.stream(declaredFields)
                .anyMatch(declaredField -> declaredField.getDeclaredAnnotation(Id.class) != null);
        if (!isPkPresent) {
            throw new IllegalArgumentException("Primary Key가 정의되지 않았습니다.");
        }
    }
}
