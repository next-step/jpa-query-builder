package persistence.sql.meta;

import java.lang.reflect.Field;

public enum FieldType {
    STRING("VARCHAR(255)", true, "getString"),
    INTEGER("INTEGER", false, "getInt"),
    LONG("BIGINT", false, "getLong");

    private final String dbType;
    private final boolean quotesNeeded;
    private final String resultSetGetterName;

    FieldType(String dbType, boolean quotesNeeded, String resultSetGetterName) {
        this.dbType = dbType;
        this.quotesNeeded = quotesNeeded;
        this.resultSetGetterName = resultSetGetterName;
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

    public String getResultSetGetterName() {
        return resultSetGetterName;
    }
}
