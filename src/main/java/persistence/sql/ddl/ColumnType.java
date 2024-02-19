package persistence.sql.ddl;

import java.util.Arrays;
import java.util.Set;

import static persistence.sql.ddl.JpaColumn.SPACE;

public enum ColumnType {

    BIGINT(Set.of(Long.class), "bigint", ""),
    VARCHAR(Set.of(String.class), "varchar", "255"),
    INTEGER(Set.of(Integer.class), "integer", ""),
    ;


    private final Set<Class<?>> supportedTypes;
    private final String value;
    private final String defaultValue;

    ColumnType(Set<Class<?>> supportedTypes, String value, String defaultValue) {
        this.supportedTypes = supportedTypes;
        this.value = value;
        this.defaultValue = defaultValue;
    }

    public static ColumnType toDdl(Class<?> type) {
        return Arrays.stream(values())
                .filter(columnType -> columnType.supportedTypes.contains(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[INFO] No supported type"));
    }

    public String getColumnDefinition() {
        StringBuilder sb = new StringBuilder();
        sb.append(SPACE).append(value);
        if(!defaultValue.isBlank()) {
            sb.append("(").append(defaultValue).append(")");
        }
        return sb.toString();
    }
}
