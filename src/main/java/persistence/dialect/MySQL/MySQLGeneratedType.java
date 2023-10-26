package persistence.dialect.MySQL;

import java.util.Arrays;

public enum MySQLGeneratedType {

    IDENTITY(" AUTO_INCREMENT");

    private final String strategy;

    MySQLGeneratedType(String strategy) {
        this.strategy = strategy;
    }
    public String getStrategy() {
        return strategy;
    }

    public static String getStrategyByTypeName(String typeName) {
        return Arrays.stream(MySQLGeneratedType.values())
                .filter(x -> x.name().equals(typeName))
                .map(MySQLGeneratedType::getStrategy)
                .findAny()
                .orElse("");
    }
}
