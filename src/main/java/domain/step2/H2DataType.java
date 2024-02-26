package domain.step2;

import java.util.Arrays;

public enum H2DataType {
    INT(Integer.class),
    BIGINT(Long.class),
    VARCHAR(String.class);

    private final Class<?> clazz;

    H2DataType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public static H2DataType from(Class<?> clazz) {
        return Arrays.stream(H2DataType.values())
                .filter(dataType -> dataType.getClazz().equals(clazz))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("지원하는 DataType 이 아닙니다."));
    }
}
