package persistence.sql.ddl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;

public enum DataType {
    INTEGER(),
    VARCHAR(),
    BIGINT();

    DataType() {
    }

    public static String of(Field field) {
        if (field.getType().equals(String.class)) {
            return VARCHAR + "(" + field.getAnnotation(Column.class).length() + ")";
        } else if (field.getType().equals(Integer.class)) {
            return INTEGER.toString();
        } else if (field.getType().equals(Long.class)) {
            return BIGINT.toString();
        }

        throw new IllegalArgumentException("Unsupported type: " + field.getType());
    }
}
