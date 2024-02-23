package persistence.sql.dialect.constraint.strategy.h2db;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import persistence.sql.dialect.constraint.strategy.ColumnConstraintStrategy;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.util.Optional.ofNullable;

public class H2GeneratedValueConstraintStrategy implements ColumnConstraintStrategy {

    private static final Map<GenerationType, String> GENERATION_TYPE_QUERY_MAP = new HashMap<>();

    static {
        GENERATION_TYPE_QUERY_MAP.put(GenerationType.IDENTITY, "AUTO_INCREMENT");
    }

    @Override
    public String generateConstraints(Field field) {
        GeneratedValue annotation = field.getDeclaredAnnotation(GeneratedValue.class);

        if (Objects.nonNull(annotation)) {
            return ofNullable(GENERATION_TYPE_QUERY_MAP.get(annotation.strategy()))
                    .orElseThrow(() -> new IllegalArgumentException("GenerationType is not supported."));
        }

        return "";
    }
}
