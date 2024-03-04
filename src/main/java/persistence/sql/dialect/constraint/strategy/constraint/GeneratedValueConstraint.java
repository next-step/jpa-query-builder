package persistence.sql.dialect.constraint.strategy.constraint;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.dialect.constraint.strategy.ColumnConstraintStrategy;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Optional.ofNullable;

public class GeneratedValueConstraint implements ColumnConstraintStrategy {

    private static final Map<GenerationType, String> GENERATION_TYPE_QUERY_MAP = new HashMap<>();
    public static final String EMPTY_GENERATED_VALUE = "";

    static {
        GENERATION_TYPE_QUERY_MAP.put(GenerationType.IDENTITY, "AUTO_INCREMENT");
    }

    @Override
    public String generateConstraints(List<Annotation> annotations) {
        GeneratedValue annotation = (GeneratedValue) annotations.stream()
                .filter(_annotation -> _annotation.annotationType().equals(GeneratedValue.class))
                .findFirst().orElse(null);

        if (Objects.nonNull(annotation)) {
            return ofNullable(GENERATION_TYPE_QUERY_MAP.get(annotation.strategy()))
                    .orElseThrow(() -> new IllegalArgumentException("GenerationType is not supported."));
        }

        return EMPTY_GENERATED_VALUE;
    }
}
