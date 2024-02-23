package database.sql.util;

import database.sql.util.type.TypeConverter;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnsMetadata {
    private final List<ColumnMetadata> allColumnMetadatas;
    private final ColumnMetadata primaryKeyColumnMetadata;
    private final List<ColumnMetadata> generalColumnMetadata;

    public ColumnsMetadata(Class<?> entityClass) {
        allColumnMetadatas = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(ColumnMetadata::new)
                .collect(Collectors.toList());

        primaryKeyColumnMetadata = allColumnMetadatas.stream()
                .filter(ColumnMetadata::isPrimaryKeyField)
                .findFirst()
                .get();

        generalColumnMetadata = allColumnMetadatas.stream()
                .filter(columnMetadata -> !columnMetadata.isPrimaryKeyField())
                .collect(Collectors.toList());
    }

    public List<String> getColumnNames() {
        List<String> list = new ArrayList<>();
        for (ColumnMetadata columnMetadata : allColumnMetadatas) {
            list.add(columnMetadata.getColumnName());
        }
        return list;
    }

    public String getJoinedColumnNames() {
        return String.join(", ", getColumnNames());
    }

    public List<String> getColumnDefinitions(TypeConverter typeConverter) {
        return allColumnMetadatas.stream()
                .map(columnMetadata -> columnMetadata.toColumnDefinition(typeConverter))
                .collect(Collectors.toList());
    }

    // TODO: 이건 미리 필터로 뽑아놔도 될듯 PK, PK 아닌거
    public Field getPrimaryKeyField() {
        return primaryKeyColumnMetadata.getField();
    }

    public String getPrimaryKeyColumnName() {
        return primaryKeyColumnMetadata.getColumnName();
    }

    public List<String> getColumnNamesForInserting() {
        List<String> list = new ArrayList<>();
        for (ColumnMetadata columnMetadata : generalColumnMetadata) {
            list.add(columnMetadata.getColumnName());
        }
        return list;
    }
}
