package database.sql.util;

import database.sql.util.column.Column;
import database.sql.util.column.GeneralColumn;
import database.sql.util.column.PrimaryKeyColumn;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

public class EntityClassInspector {
    private static final boolean DEFAULT_NULLABLE = true;
    private static final int DEFAULT_COLUMN_LENGTH = 255;

    private final Class<?> entityClass;

    public EntityClassInspector(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }
        return entityClass.getSimpleName();
    }

    public Stream<Column> getColumns() {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(this::notTransientField)
                .map(this::toColumn);
    }

    public Column getPrimaryKeyColumn() {
        return getColumns()
                .filter(Column::isPrimaryKeyField)
                .findFirst()
                .get();
    }

    public Stream<Column> getColumnsForInserting() {
        return getColumns().filter(column -> !column.isPrimaryKeyField());
    }

    private boolean notTransientField(Field field) {
        return field.getAnnotation(Transient.class) == null;
    }

    private Column toColumn(Field field) {
        jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);
        GeneratedValue generatedValueAnnotation = field.getAnnotation(GeneratedValue.class);
        boolean isId = field.isAnnotationPresent(Id.class);

        String columnName = getColumnNameFromAnnotation(columnAnnotation, field.getName());
        Class<?> type = field.getType();
        Integer columnLength = getColumnLength(columnAnnotation);

        if (isId) {
            boolean autoIncrement = isAutoIncrement(generatedValueAnnotation);
            return new PrimaryKeyColumn(columnName, type, columnLength, autoIncrement);
        } else {
            boolean nullable = isNullable(columnAnnotation);
            return new GeneralColumn(columnName, type, columnLength, nullable);
        }
    }

    private String getColumnNameFromAnnotation(jakarta.persistence.Column columnAnnotation, String defaultName) {
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        }
        return defaultName;
    }

    private Integer getColumnLength(jakarta.persistence.Column columnAnnotation) {
        if (columnAnnotation != null) {
            return columnAnnotation.length();
        }
        return DEFAULT_COLUMN_LENGTH;
    }

    private boolean isAutoIncrement(GeneratedValue generatedValueAnnotation) {
        return generatedValueAnnotation != null && generatedValueAnnotation.strategy() == GenerationType.IDENTITY;
    }

    private boolean isNullable(jakarta.persistence.Column columnAnnotation) {
        if (columnAnnotation != null) {
            return columnAnnotation.nullable();
        }
        return DEFAULT_NULLABLE;
    }
}
