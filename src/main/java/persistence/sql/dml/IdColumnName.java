package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;

public class IdColumnName {
    private final Class<?> clazz;

    public IdColumnName(final Class<?> clazz) {
        this.clazz = clazz;
    }

    String getIdColumnName() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .map(this::extractColumnName)
                .orElseThrow(() -> new IllegalArgumentException("No @Id field found in " + clazz.getName()));
    }

    private String extractColumnName(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return (column != null && !column.name().isEmpty())
                ? column.name()
                : field.getName().toLowerCase();
    }
}
