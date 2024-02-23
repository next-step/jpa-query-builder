package persistence.sql.ddl.constraint;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public class GeneratedValueH2Constraint implements H2Constraint {

    private static final String AUTO_INCREMENT_CONSTRAINT_QUERY = "AUTO_INCREMENT";

    private static final Map<GenerationType, String> strategies = Map.of(
            GenerationType.IDENTITY, AUTO_INCREMENT_CONSTRAINT_QUERY
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
                .orElseThrow(() -> new RuntimeException("지원하지 않는 전략이거나 전략값은 필수입니다."));
    }

}