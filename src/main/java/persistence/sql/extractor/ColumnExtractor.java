package persistence.sql.extractor;

import jakarta.persistence.*;
import persistence.sql.ddl.KeyType;
import persistence.sql.extractor.exception.GenerationTypeMissingException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnExtractor {
    DataTypeMapper dataTypeMapper = new DataTypeMapper();
    Class<?> clazz;

    public ColumnExtractor(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<ColumnData> createColumns() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::createColumn)
                .collect(Collectors.toList());
    }

    public List<ColumnData> createColumnsWithValue(Object entity) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> createColumn(field, entity))
                .collect(Collectors.toList());
    }

    public ColumnData createColumn(Field field) {
        Column column = field.getAnnotation(Column.class);
        ColumnData columnData = new ColumnData(getName(field, column), getDataType(field), isNullable(column));
        columnData.setKeyType(getKeyType(field, column));
        columnData.setGenerationType(getGenerationType(field));
        return columnData;
    }

    public ColumnData createColumn(Field field, Object object) {
        ColumnData columnData = createColumn(field);
        columnData.setValue(getValue(field, object));
        return columnData;
    }

    public String getName(Field field, Column column) {

        String columnName = field.getName();
        if (column != null && !column.name().isEmpty()) {
            columnName = column.name();
        }
        return columnName;
    }

    public Object getValue(Field field, Object entity) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private DataType getDataType(Field field) {
        return dataTypeMapper.map(field.getType());
    }

    private boolean isNullable(Column column) {
        if (column == null) {
            return true;
        }
        return column.nullable();
    }

    private GenerationType getGenerationType(Field field) {
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (generatedValue == null) {
            return null;
        }
        return generatedValue.strategy();
    }

    // TODO: Key 관련부분 하이버네이트 참고해보기
    private KeyType getKeyType(Field field, Column column) {
        if (isPrimaryKey(field)) {
            return KeyType.PRIMARY;
        }
        if (isUniqueKey(column)) {
            return KeyType.UNIQUE;
        }
        return null;
    }

    private boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isUniqueKey(Column column) {
        return column != null && column.unique();
    }
}
