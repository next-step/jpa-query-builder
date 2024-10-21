package persistence.sql.ddl.create.component.column;

import java.util.Arrays;

public enum ColumnDataType {
    INTEGER(Integer.class, "integer"),
    BIGINT(Long.class, "bigint"),
    VARCHAR(String.class, "varchar(100)"),
    /* TODO */
    ;

    private Class<?> fieldType;
    private String columnType;

    ColumnDataType(Class<?> fieldType, String columnType) {
        this.fieldType = fieldType;
        this.columnType = columnType;
    }

    private Class<?> getFieldType() {
        return this.fieldType;
    }

    private String getColumnType() {
        return this.columnType;
    }

    public static String convert(Class<?> fieldType) {
        return Arrays.stream(values())
                .filter(value -> fieldType.equals(value.getFieldType()))
                .findAny()
                .orElseThrow()
                .getColumnType();
    }

    /* TODO : varchar(N) */
}
