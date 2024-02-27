package persistence.sql.ddl.mapper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public enum H2DataType {

    TINYINT(List.of(Byte.class, byte.class), null),
    SMALLINT(List.of(Short.class, short.class), null),
    INTEGER(List.of(Integer.class, int.class), null),
    VARCHAR(List.of(String.class), 255),
    BIGINT(List.of(BigInteger.class, Long.class, long.class), null);

    private final List<Class<?>> supportedClasses;
    private final Integer defaultLength;

    H2DataType(List<Class<?>> classes, Integer defaultLength) {
        this.supportedClasses = classes;
        this.defaultLength = defaultLength;
    }

    public static H2DataType of(Class<?> type) {
        return Arrays.stream(values())
                .filter(h2DataType -> h2DataType.supportedClasses.contains(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported type: " + type));
    }

    public Integer getDefaultLength() {
        return defaultLength;
    }
}
