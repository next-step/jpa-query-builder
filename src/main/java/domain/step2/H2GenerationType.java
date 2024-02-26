package domain.step2;

import jakarta.persistence.GenerationType;

import java.util.Arrays;

public enum H2GenerationType {

    AUTO(GenerationType.AUTO, "auto_increment"),
    IDENTITY(GenerationType.IDENTITY, "auto_increment");
    private final GenerationType generationType;
    private final String strategy;

    H2GenerationType(GenerationType generationType, String strategy) {
        this.generationType = generationType;
        this.strategy = strategy;
    }

    public String getStrategy() {
        return strategy;
    }

    public static H2GenerationType from(GenerationType generationType) {
        return Arrays.stream(H2GenerationType.values())
                .filter(type -> type.isEqual(generationType))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("지원하는 GenerationType 이 아닙니다."));
    }

    private boolean isEqual(GenerationType target) {
        return this.generationType.equals(target);
    }
}
