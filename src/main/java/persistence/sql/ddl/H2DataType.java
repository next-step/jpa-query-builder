package persistence.sql.ddl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public enum H2DataType {

    INTEGER(List.of(Integer.class, int.class, Short.class, short.class, byte.class, Byte.class), null),
    VARCHAR(List.of(String.class), 255),
    BIGINT(List.of(BigInteger.class, Long.class, long.class), null);

    private final List<Class<?>> classes;
    private final Integer defaultLength;

    H2DataType(List<Class<?>> classes, Integer defaultLength) {
        this.classes = classes;
        this.defaultLength = defaultLength;
    }

    public static H2DataType of(Class<?> type) {
        return Arrays.stream(values())
                .filter(h2DataType -> h2DataType.classes.contains(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported type: " + type));
    }

    public Integer getDefaultLength() {
        return defaultLength;
    }
}
