package persistence.sql;

import java.lang.reflect.Field;
import java.util.Arrays;

public interface ColumnDefinitionAware {

    String name();

    String declaredName();

    default String valueAsString(Object entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        Object value = Arrays.stream(declaredFields).sequential()
                .filter(field -> field.getName().equals(declaredName()))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(entity);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Cannot access field value", e);
                    }
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        if (value instanceof String) {
            return "'" + value + "'";
        }

        return value.toString();
    }
}
