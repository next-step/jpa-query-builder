package persistence.sql.model;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;

public class EntityColumnValues {

    private final Object object;

    public EntityColumnValues(Object object) {
        this.object = object;
    }

    public String getField(String fieldName) {
        Field idField = Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> new EntityColumnName(field).getValue().equals(fieldName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ID가 존재하지 않습니다."));

        try {
            idField.setAccessible(true);
            Object fieldObject = idField.get(this.object);

            if (fieldObject instanceof String) {
                return String.format("'%s'", String.valueOf(fieldObject));
            }

            return String.valueOf(fieldObject);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
