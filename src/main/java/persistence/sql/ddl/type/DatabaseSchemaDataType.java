package persistence.sql.ddl.type;

import java.util.Arrays;
import java.util.Locale;

public enum DatabaseSchemaDataType {
    BIGINT(Long.class),
    INTEGER(Integer.class),
    VARCHAR(String.class);

    private final Class<?> type;

    DatabaseSchemaDataType(Class<?> type) {
        this.type = type;
    }

    public static DatabaseSchemaDataType from(Class<?> type) {
        return Arrays.stream(values())
                .filter(value -> value.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(type.getTypeName() + "에 맞는 데이터 타입이 존재하지 않습니다"));
    }

    public String toSQL() {
        return name().toUpperCase(Locale.ROOT);
    }
}
