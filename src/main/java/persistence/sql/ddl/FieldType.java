package persistence.sql.ddl;

import java.lang.reflect.Field;

public enum FieldType {
    STRING("VARCHAR(255)", true),
    INTEGER("INTEGER", false),
    LONG("BIGINT", false);

    private final String dbType;
    private final boolean quotesNeeded;

    FieldType(String dbType, boolean quotesNeeded) {
        this.dbType = dbType;
        this.quotesNeeded = quotesNeeded;
    }

    public static FieldType valueOf(Field field) {
        final String fieldTypeName = field.getType().getSimpleName();
        return valueOf(fieldTypeName.toUpperCase());
    }

    public String getDbType() {
        return dbType;
    }

    public boolean isQuotesNeeded() {
        return quotesNeeded;
    }
}
