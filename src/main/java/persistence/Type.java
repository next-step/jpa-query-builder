package persistence;

import java.util.Arrays;

public enum Type {
    LONG(Long.class, "bigint") {
        String expression() {
            return this.getType();
        }
    },
    STRING(String.class, "varchar") {
        String expression() {
            return this.getType() + "(255)";
        }
    },
    INTEGER(Integer.class, "bigint") {
        String expression() {
            return this.getType();
        }
    };

    private final Class<?> typeClass;
    private final String dbType;

    Type(Class<?> typeClass, String dbType) {
        this.typeClass = typeClass;
        this.dbType = dbType;
    }

    public static String valueOf(Class<?> type) {
        return Arrays.stream(values())
                .filter(it -> it.typeClass.equals(type))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .expression();
    }

    public String getType() {
        return dbType;
    }

    abstract String expression();
}
