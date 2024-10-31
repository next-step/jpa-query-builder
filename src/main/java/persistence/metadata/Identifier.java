package persistence.metadata;

import common.ErrorCode;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public record Identifier(ColumnMeta column,
                         GenerationType generationType) {

    public static Identifier from(Field[] fields) {
        Field identifierField = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_MATCH_TYPE.getErrorMsg()));
        return new Identifier(new ColumnMeta(identifierField), generationType(identifierField));
    }

    private static GenerationType generationType(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
            return annotation.strategy();
        }
        return GenerationType.IDENTITY;
    }

}
