package persistence.sql.ddl;

import java.util.Arrays;

public enum H2Type {

    INTEGER(Integer.class, "INTEGER"),

    LONG(Long.class, "BIGINT"),

    STRING(String.class, "VARCHAR(255)");

    private final Class<?> classType;
    private final String columnType;

    H2Type(Class<?> classType, String columnType) {
        this.classType = classType;
        this.columnType = columnType;
    }

    public static String converter(Class<?> type) {
        return Arrays.stream(values())
                .filter(h2 -> h2.classType.equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(type.getName() + " is not exist!"))
                .columnType;
    }
}
