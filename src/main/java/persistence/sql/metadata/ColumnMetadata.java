package persistence.sql.metadata;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
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
                .anyMatch(Column::primaryKey);

        if (!hasIdAnnotation) {
            throw new IllegalArgumentException("@Id가 필수로 지정되어야 합니다");
        }
    }

    public List<Column> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    public Column getPrimaryKey() {
        return columns.stream()
                .filter(Column::primaryKey)
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public boolean hasColumn(String fieldName) {
        return columns.stream()
                .anyMatch(column -> column.sameFieldName(fieldName));
    }

    public Column getColumn(String fieldName) {
        return columns.stream()
                .filter(column -> column.sameFieldName(fieldName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("컬럼을 찾을 수 없습니다"));
    }

    public EntityData withData(Object entity) {
        return columns.stream()
                .map(column -> column.withData(entity))
                .collect(Collectors.collectingAndThen(Collectors.toList(), EntityData::new));
    }

    public List<String> getColumnNames() {
        return columns.stream()
                .map(Column::getName)
                .toList();
    }
}
