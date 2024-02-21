package persistence.sql.ddl.model;

import java.util.Arrays;

public enum H2Type {

    INTEGER(Integer.class, "INTEGER", 0),

    LONG(Long.class, "BIGINT", 0),

    STRING(String.class, "VARCHAR", 255);

    private final Class<?> classType;
    private final String columnType;
    private final Integer length;

    H2Type(Class<?> classType, String columnType, Integer length) {
        this.classType = classType;
        this.columnType = columnType;
        this.length = length;
    }

    public static String converter(Class<?> type) {
        return Arrays.stream(values())
                .filter(h2 -> h2.classType.equals(type))
                .findAny()
                .map(H2Type::converterLength)
                .orElseThrow(() -> new IllegalArgumentException(type.getName() + " is not exist!"));
    }

    private static String converterLength(H2Type h2Type) {
        if (isDefaultLength(h2Type)) {
            return h2Type.columnType;
        }

        return h2Type.columnType + "(" + h2Type.length + ")";
    }

    private static boolean isDefaultLength(H2Type h2Type) {
        return h2Type.length == 0;
    }
}
