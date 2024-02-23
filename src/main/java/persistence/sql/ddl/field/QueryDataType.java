package persistence.sql.ddl.field;

import java.util.Arrays;
import java.util.Locale;

public enum QueryDataType {
    BIGINT(Long.class),
    INTEGER(Integer.class),
    VARCHAR(String.class);

    private final Class<?> type;

    QueryDataType(Class<?> type) {
        this.type = type;
    }

    public static String from(Class<?> type) {
        return Arrays.stream(values())
                .filter(value -> value.type.equals(type))
                .findFirst()
                .map(value -> value.name().toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new IllegalArgumentException(type.getTypeName() + "에 맞는 데이터 타입이 존재하지 않습니다"));
    }
}
