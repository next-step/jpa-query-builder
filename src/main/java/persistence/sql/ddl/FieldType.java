package persistence.sql.ddl;

import java.lang.reflect.Field;

public enum FieldType {
    STRING("VARCHAR(255)"),
    INTEGER("INTEGER"),
    LONG("BIGINT");

    private final String dbType;

    FieldType(String dbType) {
        this.dbType = dbType;
    }

    public static FieldType valueOf(Field field) {
        final String fieldTypeName = field.getType().getSimpleName();
        return valueOf(fieldTypeName.toUpperCase());
    }

    public String getDbType() {
        return dbType;
    }
}
