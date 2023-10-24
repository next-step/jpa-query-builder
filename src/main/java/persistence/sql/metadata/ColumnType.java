package persistence.sql.metadata;

import java.util.Arrays;

public enum ColumnType {

    Long(Long.class, "BIGINT"),
    String(String.class, "VARCHAR(255)"),
    Integer(Integer.class, "INTEGER");

    private Class<?> typeClass;
    private String dbTypeName;

    ColumnType(Class<?> typeClass, String dbTypeName) {
        this.typeClass = typeClass;
        this.dbTypeName= dbTypeName;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    public java.lang.String getDbTypeName() {
        return dbTypeName;
    }

    public static String convertTypeClassToName(Class<?> typeClass) {
        return Arrays.stream(ColumnType.values())
                .filter(x -> x.getTypeClass().equals(typeClass))
                .findAny()
                .get()
                .getDbTypeName();
    }
}
