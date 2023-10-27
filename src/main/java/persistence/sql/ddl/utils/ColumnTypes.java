package persistence.sql.ddl.utils;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ColumnTypes {
    final private Map<String, ColumnType> columnTypeMap;

    public ColumnTypes(final Class<?> entity) {
        this.columnTypeMap = generateColumns(entity);
    }

    public ColumnTypes(final Map<String, ColumnType> columns) {
        this.columnTypeMap = columns;
    }

    private Map<String, ColumnType> generateColumns(final Class<?> entity) {
        Map<String, ColumnType> columns = new HashMap<>();
        Field[] fields = entity.getDeclaredFields();
        for (Field field : fields) {
            ColumnType columnType;
            if (field.isAnnotationPresent(Id.class)) {
                columnType = new ColumnId(field);
            } else {
                columnType = new ColumnField(field);
            }
            columns.put(columnType.getName(), columnType);
        }
        return columns;
    }

    public ColumnType getColumn(String name) {
        return columnTypeMap.get(name);
    }

    public List<ColumnId> getIdColumns() {
        return columnTypeMap.values()
                .stream()
                .filter(ColumnType::isId)
                .filter(columnType -> !columnType.isTransient())
                .map(columnType -> (ColumnId) columnType)
                .collect(Collectors.toList());
    }

    public List<ColumnType> getFieldColumns() {
        return columnTypeMap.values()
                .stream()
                .filter(columnType -> !columnType.isId())
                .filter(columnType -> !columnType.isTransient()).collect(Collectors.toList());
    }

}
