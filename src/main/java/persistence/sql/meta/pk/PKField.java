package persistence.sql.meta.pk;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class PKField {
    private final Field field;

    public PKField(Class<?> clazz) {
        this.field = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Id 어노테이션은 반드시 존재해야합니다."));
    }

    public Field getField() {
        return field;
    }
}
