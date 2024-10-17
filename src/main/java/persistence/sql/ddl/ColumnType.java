package persistence.sql.ddl;

public enum ColumnType {
    // TODO: DB별로 타입을 확장하기
    STRING(String.class, "VARCHAR(255)"),
    INTEGER(Integer.class, "INTEGER"),
    LONG(Long.class, "BIGINT");

    private final String typeName;
    private final String sqlType;

    ColumnType(Class<?> type, String sqlType) {
        this.typeName = type.getSimpleName();
        this.sqlType = sqlType;
    }

    public String getSqlType() {
        return this.sqlType;
    }

    public static String getSqlType(Class<?> type) {
        for (ColumnType fieldType : ColumnType.values()) {
            if (fieldType.typeName.equals(type.getSimpleName())) {
                return fieldType.sqlType;
            }
        }
        throw new IllegalArgumentException("Unsupported field type: " + type.getSimpleName());
    }
}
