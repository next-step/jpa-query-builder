package persistence.sql.ddl.column;

import jakarta.persistence.GeneratedValue;

import java.util.Arrays;

public enum GenerationTypeStrategy {

    TABLE(""),
    SEQUENCE("seq.NEXTVAL "),
    IDENTITY("AUTO_INCREMENT "),
    UUID(""),
    AUTO("AUTO_INCREMENT "),
    NONE(""),
    ;

    private final String mySqlStrategyDDL;

    GenerationTypeStrategy(String mySqlStrategyDDL) {
        this.mySqlStrategyDDL = mySqlStrategyDDL;
    }

    public static GenerationTypeStrategy from(GeneratedValue generatedValue) {
        if (generatedValue == null) {
            return NONE;
        }

        return Arrays.stream(values())
                .filter(generationTypeStrategy -> generationTypeStrategy.name().equals(generatedValue.strategy().name()))
                .findFirst()
                .orElse(NONE);
    }

    public String getMySqlStrategyDDL() {
        return mySqlStrategyDDL;
    }
}
