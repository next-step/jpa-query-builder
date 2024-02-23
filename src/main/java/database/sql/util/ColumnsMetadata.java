package database.sql.util;

import database.sql.util.column.EntityColumn;
import database.sql.util.column.GeneralEntityColumn;
import database.sql.util.column.PrimaryKeyEntityColumn;
import database.sql.util.type.TypeConverter;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnsMetadata {

    private static final boolean DEFAULT_NULLABLE = true;
    private static final int DEFAULT_COLUMN_LENGTH = 255;

    private final List<Field> fields;
    private final List<EntityColumn> columns;

    public ColumnsMetadata(Class<?> entityClass) {
        fields = getFields(entityClass);
        columns = getColumns(fields);
    }

    public List<String> getColumnNames() {
        return columns.stream()
                .map(EntityColumn::getColumnName)
                .collect(Collectors.toList());
    }

    public String getJoinedColumnNames() {
        return String.join(", ", getColumnNames());
    }

    public List<String> getColumnDefinitions(TypeConverter typeConverter) {
        return columns.stream()
                .map(entityColumn -> entityColumn.toColumnDefinition(typeConverter))
                .collect(Collectors.toList());
    }

    public Field getPrimaryKeyField() {
        return fields.stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().get();
    }

    public String getPrimaryKeyColumnName() {
        return convertFieldToEntityColumn(getPrimaryKeyField()).getColumnName();
    }

    public List<String> getColumnNamesForInserting() {
        List<String> list = new ArrayList<>();
        for (EntityColumn entityColumn : columns) {
            if (!entityColumn.isPrimaryKeyField()) {
                list.add(entityColumn.getColumnName());
            }
        }
        return list;
    }

    private List<Field> getFields(Class<?> entityClass) {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .collect(Collectors.toList());
    }

    private List<EntityColumn> getColumns(List<Field> fields) {
        return fields.stream()
                .map(this::convertFieldToEntityColumn)
                .collect(Collectors.toList());
    }

    private EntityColumn convertFieldToEntityColumn(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        GeneratedValue generatedValueAnnotation = field.getAnnotation(GeneratedValue.class);
        boolean isId = field.isAnnotationPresent(Id.class);

        String columnName = getColumnNameFromAnnotation(columnAnnotation, field.getName());
        Class<?> type = field.getType();
        Integer columnLength = getColumnLength(columnAnnotation);

        if (isId) {
            boolean autoIncrement = isAutoIncrement(generatedValueAnnotation);
            return new PrimaryKeyEntityColumn(columnName, type, columnLength, autoIncrement);
        }

        boolean nullable = isNullable(columnAnnotation);
        return new GeneralEntityColumn(columnName, type, columnLength, nullable);
    }

    private String getColumnNameFromAnnotation(Column columnAnnotation, String defaultName) {
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        }
        return defaultName;
    }

    private Integer getColumnLength(Column columnAnnotation) {
        if (columnAnnotation != null) {
            return columnAnnotation.length();
        }
        return DEFAULT_COLUMN_LENGTH;
    }

    private boolean isAutoIncrement(GeneratedValue generatedValueAnnotation) {
        return generatedValueAnnotation != null && generatedValueAnnotation.strategy() == GenerationType.IDENTITY;
    }

    private boolean isNullable(Column columnAnnotation) {
        if (columnAnnotation != null) {
            return columnAnnotation.nullable();
        }
        return DEFAULT_NULLABLE;
    }
}
