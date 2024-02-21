package persistence.sql.ddl.constraint;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import static common.StringConstants.EMPTY_STRING;

public class GeneratedValueH2Constraint extends H2Constraint {

    private static final Map<GenerationType, String> strategies = Map.of(
            GenerationType.IDENTITY, "AUTO_INCREMENT"
    );

    @Override
    public String getConstraintQuery(Field field) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            return getStrategy(generatedValue);
        }
        return EMPTY_STRING;
    }

    private String getStrategy(GeneratedValue generatedValue) {
        return Optional.ofNullable(strategies.get(generatedValue.strategy()))
                .orElse(EMPTY_STRING);

    }

}
