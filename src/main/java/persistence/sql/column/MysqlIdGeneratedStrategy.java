package persistence.sql.column;

import jakarta.persistence.GenerationType;

import java.util.Arrays;

public enum MysqlIdGeneratedStrategy implements IdGeneratedStrategy {
    IDENTITY("auto_increment", jakarta.persistence.GenerationType.IDENTITY),
    ;
    private final String value;
    private final jakarta.persistence.GenerationType generationType;

    MysqlIdGeneratedStrategy(String value, jakarta.persistence.GenerationType generationType) {
        this.value = value;
        this.generationType = generationType;
    }

    public static MysqlIdGeneratedStrategy from(GenerationType strategy) {
        return Arrays.stream(values())
                .filter(mysqlIdGenerateStrategy -> mysqlIdGenerateStrategy.generationType.equals(strategy))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found GenerationType"));
    }

    @Override
    public String getValue() {
        return value;
    }
}
