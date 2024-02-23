package persistence.sql.ddl.column;

import java.time.LocalDate;
import java.util.Arrays;

public enum ColumnType {

    STRING(String.class, "VARCHAR"),
    INTEGER(Integer.class, "INT"),
    LONG(Long.class, "BIGINT"),
    LOCALDATE(LocalDate.class, "DATETIME"),
    BOOLEAN(Boolean.class, "BIT"),
    ;

    private final Class<?> fieldType;
    private final String mysqlColumnType;

    ColumnType(Class<?> fieldType, String mysqlColumnType) {
        this.fieldType = fieldType;
        this.mysqlColumnType = mysqlColumnType;
    }

    public static ColumnType findColumnType(Class<?> field) {
        return Arrays.stream(values())
                .filter(columnType -> columnType.fieldType == field)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("invalid field type: %s", field.getName())));
    }

    public String getMysqlColumnType() {
        return mysqlColumnType;
    }
}
