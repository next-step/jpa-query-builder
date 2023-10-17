package persistence.sql.ddl;

import jakarta.persistence.Id;
import persistence.sql.meta.EntityMeta;

import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class CreateQueryBuilder {

    public abstract String getQuery(Class<?> clazz);

    protected void validateEntity(Class<?> clazz) {
        validateEntityAnnotation(clazz);
        validatePkAnnotation(clazz);
    }

    private void validateEntityAnnotation(Class<?> clazz) {
        if (!EntityMeta.isEntity(clazz)) {
            throw new IllegalArgumentException("Create Query 빌드 대상이 아닙니다.");
        }
    }

    private void validatePkAnnotation(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        boolean isPkPresent = Arrays.stream(declaredFields)
                .anyMatch(declaredField -> declaredField.getDeclaredAnnotation(Id.class) != null);
        if (!isPkPresent) {
            throw new IllegalArgumentException("Primary Key가 정의되지 않았습니다.");
        }
    }

}
