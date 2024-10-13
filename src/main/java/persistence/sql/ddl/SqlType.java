package persistence.sql.ddl;

public enum SqlType {
    VARCHAR,
    INTEGER,
    BIGINT,
    ;

    public static SqlType from(String type) {
        return switch (type) {
            case "Long" -> BIGINT;
            case "String" -> VARCHAR;
            case "Integer" -> INTEGER;
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
