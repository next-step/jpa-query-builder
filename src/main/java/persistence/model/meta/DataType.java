package persistence.model.meta;

import java.util.Arrays;

public enum DataType {
    VARCHAR(String.class, "varchar(255)"),

    BIGINT(Long.class, "bigint"),

    INT(Integer.class, "int");

    private final Class<?> javaType;

    private final String defaultName;

    DataType(Class<?> javaType, String defaultName) {
        this.javaType = javaType;
        this.defaultName = defaultName;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public static DataType getByJavaType(Class<?> javaType) {
        return Arrays.stream(DataType.values())
                .filter(dataType -> dataType.getJavaType().equals(javaType))
                .findFirst()
                .orElseThrow();
    }
}
