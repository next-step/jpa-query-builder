package persistence.sql.ddl;

import java.util.Arrays;

enum ColumnType {
    BIGINT(Long.class),
    INTEGER(Integer.class),
    VARCHAR(String.class);

    private final Class<?> javaType;

    ColumnType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public static ColumnType fromJavaType(Class<?> type) {
        return Arrays.stream(values())
                .filter(columnType -> columnType.javaType.equals(type))
                .findFirst()
                .orElse(VARCHAR);
    }

    public String getDefinition(DatabaseDialect dialect) {
        return dialect.getColumnTypeDefinition(this);
    }
}
