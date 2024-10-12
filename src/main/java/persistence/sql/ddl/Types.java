package persistence.sql.ddl;

import java.util.Arrays;

public enum Types {
    INTEGER(Integer.class),
    VARCHAR(String.class),
    BIGINT(Long.class);

    private final Class<?> fieldType;

    Types(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public static Types findByType(Class<?> fieldType) {
        return Arrays.stream(values())
                .filter(type -> type.getFieldType().equals(fieldType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported field type: " + fieldType.getName()));
    }
}
