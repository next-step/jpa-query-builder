package database.sql.util;

import database.sql.util.column.EntityColumn;
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
                .map(field1 -> new FieldToEntityColumnConverter(field1).convert())
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

    public List<String> getColumnNamesForInserting() {
        return generalColumns.stream().map(EntityColumn::getColumnName).collect(Collectors.toList());
    }

    public Object getPrimaryKeyValue(Object entity) throws IllegalAccessException {
        return primaryKey.getValue(entity);
    }

}
