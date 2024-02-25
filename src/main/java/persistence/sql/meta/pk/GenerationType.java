package persistence.sql.meta.pk;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public enum GenerationType {
    AUTO,
    IDENTITY;

    public static GenerationType of(Class<?> clazz) {
        Field pkField = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Id 어노테이션은 반드시 존재해야합니다."));
        if (!pkField.isAnnotationPresent(jakarta.persistence.GeneratedValue.class)) {
            return AUTO;
        }
        return mappingJPAtoJPAImpl(pkField.getAnnotation(GeneratedValue.class).strategy());
    }

    private static GenerationType mappingJPAtoJPAImpl(jakarta.persistence.GenerationType before) {
        return Arrays.stream(GenerationType.values())
                .filter(generationType -> before.name().equals(generationType.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 GenerationType 입니다."));
    }

}
