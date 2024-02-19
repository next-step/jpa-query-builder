package persistence.sql.ddl.mysql;

import java.util.Arrays;
import java.util.Objects;

public enum MySQLColumnType {

    STRING(String.class, "VARCHAR", 255),
    INTEGER(Integer.class, "INT", null),
    LONG(Long.class, "BIGINT", null);

    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";

    private final Class<?> type;
    private final String keyword;
    private final Integer defaultSize;

    MySQLColumnType(Class<?> type, String keyword, Integer defaultSize) {
        this.type = type;
        this.keyword = keyword;
        this.defaultSize = defaultSize;
    }

    public static String convert(Class<?> type, Integer size) {
        return Arrays.stream(values())
                .filter(value -> value.type.equals(type))
                .findFirst()
                .map(value -> {
                    if (value.defaultSize == null) {
                        return value.keyword;
                    }
                    return value.keyword + OPEN_BRACKET + Objects.requireNonNullElseGet(size, () -> value.defaultSize) + CLOSE_BRACKET;

                })
                .orElseThrow(IllegalArgumentException::new);

    }
}
