package persistence.sql.ddl.column;

import java.time.LocalDate;
import java.util.Arrays;

public enum ColumnType {

    STRING(String.class, "VARCHAR", true),
    INTEGER(Integer.class, "INT", false),
    LONG(Long.class, "BIGINT", false),
    LOCALDATE(LocalDate.class, "DATETIME", true),
    BOOLEAN(Boolean.class, "BIT", false),
    ;

    private final Class<?> fieldType;
    private final String mysqlColumnType;
    private final boolean requireQuotes;

    ColumnType(Class<?> fieldType, String mysqlColumnType, boolean requireQuotes) {
        this.fieldType = fieldType;
        this.mysqlColumnType = mysqlColumnType;
        this.requireQuotes = requireQuotes;
    }

    public static ColumnType findColumnType(Class<?> type) {
        return Arrays.stream(values())
                .filter(columnType -> columnType.fieldType == type)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("invalid field type: %s", type.getName())));
    }

    public static boolean requireQuotes(Class<?> type) {
        return Arrays.stream(values())
                .filter(columnType -> columnType.fieldType == type)
                .map(columnType -> columnType.requireQuotes)
                .findFirst()
                .orElse(false);
    }

    public String getMysqlColumnType() {
        return mysqlColumnType;
    }
}
