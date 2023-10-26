package persistence.dialect.MySQL;

import java.util.Arrays;

public enum MySQLColumnType {

    Long(Long.class, "BIGINT"),
    String(String.class, "VARCHAR(255)"),
    Integer(Integer.class, "INTEGER");

    private final Class<?> typeClass;
    private final String dbTypeName;

    MySQLColumnType(Class<?> typeClass, String dbTypeName) {
        this.typeClass = typeClass;
        this.dbTypeName= dbTypeName;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    public java.lang.String getDbTypeName() {
        return dbTypeName;
    }

    public static String getColumnTypeByClass(Class<?> typeClass) {
        return Arrays.stream(MySQLColumnType.values())
                .filter(x -> x.getTypeClass().equals(typeClass))
                .findAny()
                .get()
                .getDbTypeName();
    }
}
