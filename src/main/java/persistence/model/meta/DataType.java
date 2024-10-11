package persistence.model.meta;

import java.util.Arrays;

public enum DataType {
    VARCHAR(String.class, "varchar(%d)", 255),

    BIGINT(Long.class, "bigint"),

    INT(Integer.class, "int");

    private final Class<?> javaType;

    private final String namePattern;

    private int defaultLength = -1;

    DataType(Class<?> javaType, String namePattern, int defaultLength) {
        this.javaType = javaType;
        this.namePattern = namePattern;
        this.defaultLength = defaultLength;
    }

    DataType(Class<?> javaType, String namePattern) {
        this.javaType = javaType;
        this.namePattern = namePattern;
    }

    public static DataType getByJavaType(Class<?> javaType) {
        return Arrays.stream(DataType.values())
                .filter(dataType -> dataType.getJavaType().equals(javaType))
                .findFirst()
                .orElseThrow();
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public String getDefaultName() {
        return namePattern;
    }

    public int getDefaultLength() {
        return defaultLength;
    }

    public String getFullName(int length) {
        if (namePattern.contains("%d")) {
            return String.format(namePattern, length);
        }
        return namePattern;
    }
}
