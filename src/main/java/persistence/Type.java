package persistence;

import java.util.Arrays;

public enum Type {
    LONG(Long.class, "bigint") {
        String expression(int size) {
            return this.getType();
        }
    },
    STRING(String.class, "varchar") {
        String expression(int size) {
            return String.format("%s(%d)", this.getType(), size);
        }
    },
    INTEGER(Integer.class, "integer") {
        String expression(int size) {
            return this.getType();
        }
    };

    private final Class<?> typeClass;
    private final String dbType;

    Type(Class<?> typeClass, String dbType) {
        this.typeClass = typeClass;
        this.dbType = dbType;
    }

    public static String valueOf(Class<?> type, int size) {
        return Arrays.stream(values())
                .filter(it -> it.typeClass.equals(type))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .expression(size);
    }

    public String getType() {
        return dbType;
    }

    abstract String expression(int size);
}
