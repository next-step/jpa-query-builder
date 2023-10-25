package persistence.dialect.H2;

import java.util.Arrays;

public enum H2ColumnType {

    Long(Long.class, "BIGINT"),
    String(String.class, "VARCHAR(255)"),
    Integer(Integer.class, "INTEGER");

    private Class<?> typeClass;
    private String dbTypeName;

    H2ColumnType(Class<?> typeClass, String dbTypeName) {
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
        return Arrays.stream(H2ColumnType.values())
                .filter(x -> x.getTypeClass().equals(typeClass))
                .findAny()
                .get()
                .getDbTypeName();
    }
}
