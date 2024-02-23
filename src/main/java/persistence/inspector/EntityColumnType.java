package persistence.inspector;

public enum EntityColumnType {

    STRING(String.class, "VARCHAR(255)"),
    INT(int.class, "INT"),
    INTEGER(Integer.class, "INT"),
    LONG(Long.class, "BIGINT"),
    ;

    private final Class<?> type;
    private final String mysqlType;

    EntityColumnType(Class<?> dataType, String mysqlType) {
        this.type = dataType;
        this.mysqlType = mysqlType;
    }

    public String getMysqlType() {
        return mysqlType;
    }

    public static EntityColumnType get(Class<?> type) {
        for (EntityColumnType columnType : EntityColumnType.values()) {
            if (columnType.type.equals(type)) {
                return columnType;
            }
        }
        return STRING;
    }
}
