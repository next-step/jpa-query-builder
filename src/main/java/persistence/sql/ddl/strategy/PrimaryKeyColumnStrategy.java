package persistence.sql.ddl.strategy;

import jakarta.persistence.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PrimaryKeyColumnStrategy extends CommonColumnStrategy {
    private static final String PRIMARY_KEY = "PRIMARY KEY";

    @Override
    public boolean isRequired(Field field) {
        return Arrays.stream(field.getDeclaredAnnotations())
                .map(Annotation::annotationType)
                .collect(Collectors.toList())
                .contains(Id.class);
    }

    @Override
    public String fetchQueryPart() {
        return SPACE + PRIMARY_KEY;
    }
}
