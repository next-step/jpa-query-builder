package persistence.sql.metadata;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import persistence.exception.NotExistException;
import persistence.sql.ddl.query.CreateQueryColumn;

public record Identifier(CreateQueryColumn column,
                         GenerationType generationType) {

    public static Identifier from(Field[] fields) {
        Field identifierField = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new NotExistException("identification."));
        return new Identifier(new CreateQueryColumn(identifierField), generationType(identifierField));
    }

    private static GenerationType generationType(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
            return annotation.strategy();
        }
        return GenerationType.IDENTITY;
    }

}
