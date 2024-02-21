package persistence.sql.ddl;

import java.time.LocalDate;
import java.util.Arrays;

public enum ColumnType {

    VARCHAR(String.class),
    INT(Integer.class),
    BIGINT(Long.class),
    DATETIME(LocalDate.class),
    BIT(Boolean.class);


    private final Class<?> fieldType;

    ColumnType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public static ColumnType findColumnType(Class<?> field) {
        return Arrays.stream(values())
                .filter(columnType -> columnType.fieldType == field)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Invalid field Type: %s", field.getTypeName())));
    }
}
