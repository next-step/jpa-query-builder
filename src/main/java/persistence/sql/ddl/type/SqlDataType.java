package persistence.sql.ddl.type;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public enum SqlDataType {
    INTEGER(Integer.class, int.class, Short.class, short.class, byte.class, Byte.class),
    VARCHAR(String.class),
    BIGINT(BigInteger.class, Long.class, long.class);

    SqlDataType(Class<?>... supportedClasses) {
        this.supportedClasses = List.of(supportedClasses);
    }

    private final List<Class<?>> supportedClasses;

    public static SqlDataType of(Field field) {
        return Arrays.stream(values())
            .filter(sqlDataType -> sqlDataType.isSupported(field.getType()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unsupported type: " + field.getType()));
    }

    public boolean isSupported(Class<?> clazz) {
        return supportedClasses.contains(clazz);
    }
}
