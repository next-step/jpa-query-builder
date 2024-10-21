package H2QueryBuilder;

import common.ErrorCode;
import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class TableColumnAttribute {
    private String columnName;
    private String columnDataType;
    private boolean isPrimeKey;
    private boolean isNotNull;
    private boolean isAutoIncrement;
    private boolean isTransient;
    private String fieldValue;
    private final TableColumnAttributes tableColumnAttributes = new TableColumnAttributes();

    public TableColumnAttribute() {
    }

    public TableColumnAttribute(String columnName, Class<?> columnDataType, boolean isPrimeKey, boolean isNotNull, boolean isAutoIncrement, boolean isTransient) {
        this.columnName = columnName;
        this.columnDataType = H2DataType.findH2DataTypeByDataType(columnDataType);
        this.isPrimeKey = isPrimeKey;
        this.isNotNull = isNotNull;
        this.isAutoIncrement = isAutoIncrement;
        this.isTransient = isTransient;
    }

    public TableColumnAttribute(String columnName, Class<?> columnDataType, boolean isPrimeKey, boolean isNotNull, boolean isAutoIncrement, boolean isTransient, String value) {
        this.columnName = columnName;
        this.columnDataType = H2DataType.findH2DataTypeByDataType(columnDataType);
        this.isPrimeKey = isPrimeKey;
        this.isNotNull = isNotNull;
        this.isAutoIncrement = isAutoIncrement;
        this.isTransient = isTransient;
        this.fieldValue = value;
    }

    public void generateTableColumnMeta(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            tableColumnAttributes.add(new TableColumnAttribute(getColumnName(field), field.getType(), true, getColumnNullable(field), isGenerateValueIdentity(field), isTransient(field)));
        } else {
            tableColumnAttributes.add(new TableColumnAttribute(getColumnName(field), field.getType(), false, !getColumnNullable(field), isGenerateValueIdentity(field), isTransient(field)));
        }
    }

    public void generateTableColumnMeta(Field field, Object object) throws IllegalAccessException {
        if (field.isAnnotationPresent(Id.class)) {
            tableColumnAttributes.add(new TableColumnAttribute(getColumnName(field), field.getType(), true, getColumnNullable(field), isGenerateValueIdentity(field), isTransient(field), getValue(field, object)));
        } else {
            tableColumnAttributes.add(new TableColumnAttribute(getColumnName(field), field.getType(), false, !getColumnNullable(field), isGenerateValueIdentity(field), isTransient(field), getValue(field, object)));
        }
    }

    public String getColumnName() {
        return this.columnName;
    }

    public String getColumnDataType() {
        return this.columnDataType;
    }

    public boolean isPrimeKey() {
        return this.isPrimeKey;
    }

    public boolean isNotNull() {
        return this.isNotNull;
    }

    public boolean isAutoIncrement() {
        return this.isAutoIncrement;
    }

    public boolean isTransient() {
        return this.isTransient;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public List<TableColumnAttribute> getTableAttributes() {
        return this.tableColumnAttributes.getTableColumnAttributes();
    }

    public boolean getColumnNullable(Field field) {
        Column column = field.getAnnotation(Column.class);
        return !field.isAnnotationPresent(Column.class) || column.nullable();
    }

    public String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            return column.name().isEmpty() ? field.getName() : column.name();
        }
        return field.getName();
    }

    public boolean isGenerateValueIdentity(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return false;
        }

        return field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY;
    }

    public boolean isTransient(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    public String getValue(Field field, Object object) throws IllegalAccessException {
        field.setAccessible(true);
        String fieldValue;
        try {
            fieldValue = Optional.ofNullable(field.get(object))
                    .map(value -> {
                        if (H2DataType.findH2DataTypeByDataType(field.getType()).equals("VARCHAR")) {
                            return "'" + value + "'";
                        }
                        return value.toString();
                    })
                    .orElse("NULL");
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            throw new IllegalAccessException(ErrorCode.ACCESS_NOT_PERMITTED.getErrorMsg());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorCode.NOT_MATCH_TYPE.getErrorMsg());
        } catch (NullPointerException e) {
            throw new NullPointerException(ErrorCode.ENTITY_IS_NULL.getErrorMsg());
        }
        return fieldValue;
    }
}
