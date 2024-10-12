package persistence.sql.ddl;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnMetadata {
    private final List<Column> columns;

    private ColumnMetadata(List<Column> columns) {
        validate(columns);
        this.columns = columns;
    }

    public static ColumnMetadata from(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(ColumnMetadata::isNotTransient)
                .map(Column::from)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ColumnMetadata::new));
    }

    private static boolean isNotTransient(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private void validate(List<Column> columns) {
        boolean hasIdAnnotation = columns.stream()
                .anyMatch(Column::isPrimaryKey);

        if (!hasIdAnnotation) {
            throw new IllegalArgumentException("@Id가 필수로 지정되어야 합니다");
        }
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Column> getPrimaryKeys() {
        return columns.stream()
                .filter(Column::isPrimaryKey)
                .toList();
    }
}
