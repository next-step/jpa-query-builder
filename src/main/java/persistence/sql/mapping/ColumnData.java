package persistence.sql.mapping;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import persistence.sql.ddl.KeyType;
import persistence.sql.mapping.exception.GenerationTypeMissingException;

import java.lang.reflect.Field;

public class ColumnData {
    private final String name;
    private final DataType type;
    private Object value;
    private final KeyType keyType;
    private final GenerationType generationType;
    private final boolean isNullable;

    private ColumnData(String name, DataType type, KeyType keyType, GenerationType generationType, boolean isNullable) {
        this.name = name;
        this.type = type;
        this.keyType = keyType;
        this.generationType = generationType;
        this.isNullable = isNullable;
    }

    public static ColumnData createColumn(Field field) {
        Column column = field.getAnnotation(Column.class);
        return new ColumnData(
                extractName(field, column),
                extractDataType(field),
                extractKeyType(field, column),
                extractGenerationType(field),
                extractIsNullable(column)
        );
    }

    public static ColumnData createColumnWithValue(Field field, Object object) {
        ColumnData columnData = createColumn(field);
        columnData.setValue(extractValue(field, object));
        return columnData;
    }

    public boolean isPrimaryKey() {
        return keyType == KeyType.PRIMARY;
    }

    public boolean isNotPrimaryKey() {
        return !isPrimaryKey();
    }

    private void setValue(Object value) {
        this.value = value;
    }

    private static String extractName(Field field, Column column) {
        String columnName = field.getName();
        if (column != null && !column.name().isEmpty()) {
            columnName = column.name();
        }
        return columnName;
    }

    private static Object extractValue(Field field, Object entity) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static DataType extractDataType(Field field) {
        return new DataTypeMapper().map(field.getType());
    }

    private static boolean extractIsNullable(Column column) {
        if (column == null) {
            return true;
        }
        return column.nullable();
    }

    private static GenerationType extractGenerationType(Field field) {
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (generatedValue == null) {
            return null;
        }
        return generatedValue.strategy();
    }

    // TODO: Key 관련부분 하이버네이트 참고해보기
    private static KeyType extractKeyType(Field field, Column column) {
        if (extractIsPrimaryKey(field)) {
            return KeyType.PRIMARY;
        }
        return null;
    }

    private static boolean extractIsPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public DataType getType() {
        return type;
    }

    public boolean isNotNullable() {
        return !isNullable;
    }

    public boolean hasGenerationType() {
        return generationType != null;
    }

    public boolean hasKeyType() {
        return keyType != null;
    }

    public GenerationType getGenerationType() {
        if(!hasGenerationType()) {
            throw new GenerationTypeMissingException();
        }
        return generationType;
    }

    public KeyType getKeyType() {
        return keyType;
    }
}
