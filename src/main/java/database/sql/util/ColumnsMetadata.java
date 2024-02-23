package database.sql.util;

import database.sql.util.column.EntityColumn;
import database.sql.util.column.FieldToEntityColumnConverter;
import database.sql.util.type.TypeConverter;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnsMetadata {

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
        return columns.stream()
                .filter(EntityColumn::isPrimaryKeyField)
                .findFirst()
                .get()
                .getColumnName();
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
        return new FieldToEntityColumnConverter(field).convert();
    }
}
