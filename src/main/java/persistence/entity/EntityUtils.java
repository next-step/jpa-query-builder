package persistence.entity;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class EntityUtils {

    public static Long getIdValue(Object entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        Field idField = Arrays.stream(declaredFields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findAny()
                .orElseThrow();
        idField.setAccessible(true);
        try {
            return (Long) idField.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
