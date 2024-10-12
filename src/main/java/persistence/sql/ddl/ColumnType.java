package persistence.sql.ddl;

import java.util.Arrays;

public enum ColumnType {
    BIGINT(Long.class, "bigint"),
    ;

    private final Class<?> javaType;
    private final String columnDefinition;

    ColumnType(Class<?> javaType, String columnDefinition) {
        this.javaType = javaType;
        this.columnDefinition = columnDefinition;
    }

    public static ColumnType of(Class<?> javaType) {
        return Arrays.stream(values())
                .filter(v -> v.javaType.equals(javaType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No column type found for " + javaType));
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }
}
