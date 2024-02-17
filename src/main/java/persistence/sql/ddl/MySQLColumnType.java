package persistence.sql.ddl;

import java.util.Arrays;

public enum MySQLColumnType {

    STRING(String.class, "VARCHAR", 255),
    INTEGER(Integer.class, "INT", null),
    LONG(Long.class, "BIGINT", null);

    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";

    private final Class<?> type;
    private final String keyword;
    private final Integer size;

    MySQLColumnType(Class<?> type, String keyword, Integer size) {
        this.type = type;
        this.keyword = keyword;
        this.size = size;
    }

    public static String convert(Class<?> type) {
        return Arrays.stream(values())
                .filter(value -> value.type.equals(type))
                .findFirst()
                .map(value -> {
                    if (value.size == null) {
                        return value.keyword;
                    }
                    return value.keyword + OPEN_BRACKET + value.size + CLOSE_BRACKET;

                })
                .orElseThrow(IllegalArgumentException::new);

    }
}
