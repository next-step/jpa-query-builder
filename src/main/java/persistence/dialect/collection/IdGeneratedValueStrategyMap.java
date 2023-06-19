package persistence.dialect.collection;

import jakarta.persistence.GenerationType;

import java.util.Map;

public class IdGeneratedValueStrategyMap {
    private final Map<GenerationType, String> GENERATED_STRATEGY_MAP;

    public IdGeneratedValueStrategyMap() {
        GENERATED_STRATEGY_MAP = Map.of(
                GenerationType.IDENTITY, "auto_increment"
        );
    }

    public String get(GenerationType generationType) {
        final String strategy = GENERATED_STRATEGY_MAP.get(generationType);
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy for type " + generationType);
        }
        return strategy;
    }
}
