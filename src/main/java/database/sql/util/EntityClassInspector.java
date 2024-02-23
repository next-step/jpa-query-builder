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

    private List<Field> getFields() {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(this::notTransientField)
                .collect(Collectors.toList());
    }

    private List<EntityColumn> getColumns() {
        return getFields().stream()
                .map(this::fieldToColumn)
                .collect(Collectors.toList());
    }

    public List<String> getColumnNames() {
        return getColumns().stream()
                .map(EntityColumn::getColumnName)
                .collect(Collectors.toList());
    }

    public String getJoinedColumnNames() {
        return String.join(", ", getColumnNames());
    }

    public List<String> getColumnDefinitions(TypeConverter typeConverter) {
        List<String> list = new ArrayList<>();
        for (EntityColumn entityColumn : getColumns()) {
            list.add(entityColumn.toColumnDefinition(typeConverter));
        }
        return list;
    }

    public Field getPrimaryKeyField() {
        return getFields().stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().get();
    }

    public String getPrimaryKeyColumnName() {
        for (EntityColumn entityColumn : getColumns()) {
            if (entityColumn.isPrimaryKeyField()) {
                return entityColumn.getColumnName();
            }
        }
        throw new UnsupportedOperationException("primary key 컬럼이 없는 경우는 지원하지 않습니다.");
    }

    public List<String> getColumnNamesForInserting() {
        List<String> list = new ArrayList<>();
        for (EntityColumn entityColumn : getColumns()) {
            if (!entityColumn.isPrimaryKeyField()) {
                list.add(entityColumn.getColumnName());
            }
        }
        return list;
    }

    private boolean notTransientField(Field field) {
        return field.getAnnotation(Transient.class) == null;
    }

    private EntityColumn fieldToColumn(Field field) {
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
