package persistence.sql.ddl;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.util.Optional;

public enum DataType {
    INTEGER(),
    VARCHAR(),
    BIGINT();

    DataType() {
    }

    public static String of(Field field) {
        if (field.getType().equals(String.class)) {
            if (!field.isAnnotationPresent(Column.class)) {
                return VARCHAR + "(255)";
            }

            return VARCHAR + "(" + Optional.ofNullable(field.getAnnotation(Column.class)).map(Column::length).orElse(255)+ ")";
        } else if (field.getType().equals(Integer.class)) {
            return INTEGER.toString();
        } else if (field.getType().equals(Long.class)) {
            return BIGINT.toString();
        }

        throw new IllegalArgumentException("Unsupported type: " + field.getType());
    }
}
