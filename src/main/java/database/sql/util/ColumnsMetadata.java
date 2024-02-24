package database.sql.util;

import database.sql.util.column.EntityColumn;
import database.sql.util.column.FieldToEntityColumnConverter;
import database.sql.util.type.TypeConverter;
import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnsMetadata {

    private final List<EntityColumn> allEntityColumns;
    private final EntityColumn primaryKey;
    private final List<EntityColumn> generalColumns;

    public ColumnsMetadata(Class<?> entityClass) {
        allEntityColumns = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> new FieldToEntityColumnConverter(field).convert())
                .collect(Collectors.toList());

        primaryKey = allEntityColumns.stream()
                .filter(EntityColumn::isPrimaryKeyField).findFirst().get();

        generalColumns = allEntityColumns.stream()
                .filter(columnMetadata -> !columnMetadata.isPrimaryKeyField())
                .collect(Collectors.toList());
    }

    public List<String> getColumnNames() {
        return allEntityColumns.stream().map(EntityColumn::getColumnName).collect(Collectors.toList());
    }

    public String getJoinedColumnNames() {
        return String.join(", ", getColumnNames());
    }

    public List<String> getColumnDefinitions(TypeConverter typeConverter) {
        return allEntityColumns.stream()
                .map(entityColumn -> entityColumn.toColumnDefinition(typeConverter))
                .collect(Collectors.toList());
    }

    public String getPrimaryKeyColumnName() {
        return primaryKey.getColumnName();
    }

    // 컬럼들 분류는 pk, columsn 이렇게 두개로
    // Inserting 이란 이름 대신 그냥 컬럼으로.
    public List<String> getColumnNamesForInserting() {
        return generalColumns.stream().map(EntityColumn::getColumnName).collect(Collectors.toList());
    }

    public List<EntityColumn> getColumnsForInserting() {
        return generalColumns;
    }

    public long getPrimaryKeyValue(Object entity) {
        try {
            return (long) primaryKey.getValue(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
