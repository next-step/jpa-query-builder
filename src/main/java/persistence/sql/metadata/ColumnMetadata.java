package persistence.sql.metadata;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnMetadata {
    private final List<Column> columns;
    private final List<Column> insertColumns;

    private ColumnMetadata(List<Column> columns) {
        validate(columns);
        this.columns = columns;
        this.insertColumns = getInsertColumns(columns);
    }

    private List<Column> getInsertColumns(List<Column> columns) {
        return columns.stream()
                .filter(Column::hasNotIdentityStrategy)
                .toList();
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

    public List<String> getInsertColumnNames() {
        return insertColumns.stream()
                .map(Column::getName)
                .toList();
    }

    public List<String> extractInsertColumnValues(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(this::isInsertColumnName)
                .map(field -> getValue(entity, field))
                .map(ColumnValue::toString)
                .toList();
    }

    private boolean isInsertColumnName(Field field) {
        return insertColumns.stream()
                .anyMatch(column -> column.sameName(field));
    }

    private ColumnValue getValue(Object object, Field field) {
        field.setAccessible(true);
        try {
            return new ColumnValue(field.get(object));
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("접근할 수 없는 필드입니다: " + field.getName());
        }
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

    public ColumnValue extractPrimaryKeyValue(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(this::isPrimaryKey)
                .findFirst()
                .map(field -> getValue(entity, field))
                .orElseThrow(() -> new IllegalStateException("Id 필드를 찾을 수 없습니다"));
    }

    private boolean isPrimaryKey(Field field) {
        return getPrimaryKey().sameName(field);
    }
}
