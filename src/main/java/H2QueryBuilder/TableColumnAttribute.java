package H2QueryBuilder;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.List;

public class TableColumnAttribute {
    private String columnName;
    private String columnDataType;
    private boolean isPrimeKey;
    private boolean isNotNull;
    private boolean isAutoIncrement;
    private final TableColumnAttributes tableColumnAttributes = new TableColumnAttributes();

    public TableColumnAttribute() {
    }

    public TableColumnAttribute(String columnName, Class<?> columnDataType, boolean isPrimeKey, boolean isNotNull, boolean isAutoIncrement) {
        this.columnName = columnName;
        this.columnDataType = H2DataType.findH2DataTypeByDataType(columnDataType);
        this.isPrimeKey = isPrimeKey;
        this.isNotNull = isNotNull;
        this.isAutoIncrement = isAutoIncrement;
    }

    public void generateTableColumnMeta(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            tableColumnAttributes.add(new TableColumnAttribute(getColumnName(field), field.getType(), true, getColumnNullable(field), isGenerateValueIdentity(field)));
        } else {
            tableColumnAttributes.add(new TableColumnAttribute(getColumnName(field), field.getType(), false, !getColumnNullable(field), isGenerateValueIdentity(field)));
        }

    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnDataType() {
        return columnDataType;
    }

    public boolean isPrimeKey() {
        return isPrimeKey;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
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
}
