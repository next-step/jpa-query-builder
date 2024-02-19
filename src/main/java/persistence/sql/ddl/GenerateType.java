package persistence.sql.ddl;

import jakarta.persistence.GenerationType;

import java.util.Arrays;

public enum GenerateType {
    IDENTITY("auto_increment", jakarta.persistence.GenerationType.IDENTITY),
    ;
    private final String value;
    private final jakarta.persistence.GenerationType generationType;

    GenerateType(String value, jakarta.persistence.GenerationType generationType) {
        this.value = value;
        this.generationType = generationType;
    }

    public static GenerateType from(GenerationType strategy) {
        return Arrays.stream(values())
                .filter(generateType -> generateType.generationType.equals(strategy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found GenerationType"));
    }

    public String getValue() {
        return value;
    }
}
