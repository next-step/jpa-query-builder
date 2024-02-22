package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;

import java.util.Arrays;

public enum GenerationTypeStrategy {

    TABLE(""),
    SEQUENCE("seq.NEXTVAL"),
    IDENTITY("AUTO_INCREMENT"),
    UUID(""),
    AUTO("AUTO_INCREMENT"),
    ;

    private final String mySqlStrategyDDL;

    GenerationTypeStrategy(String mySqlStrategyDDL) {
        this.mySqlStrategyDDL = mySqlStrategyDDL;
    }

    public static GenerationTypeStrategy from(GeneratedValue generatedValue) {
        if (generatedValue == null) {
            return null;
        }

        return Arrays.stream(values())
                .filter(generationTypeStrategy -> generationTypeStrategy.name().equals(generatedValue.strategy().name()))
                .findFirst()
                .orElse(null);
    }

    public String getMySqlStrategyDDL() {
        return mySqlStrategyDDL;
    }
}
