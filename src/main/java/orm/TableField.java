package orm;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import orm.util.StringUtils;

import java.lang.reflect.Field;

public class TableField {

    private final Field field;
    private final String fieldName;
    private final boolean isId;
    private final ColumnMeta columnMeta;

    public TableField(Field field) {
        Column column = field.getAnnotation(Column.class);
        this.field = field;
        this.fieldName = initFieldName(column, field);
        this.isId = field.getAnnotation(Id.class) != null;
        this.columnMeta = ColumnMeta.from(column);
    }

    public String getFieldName() {
        return fieldName;
    }

    public ColumnMeta getColumnMeta() {
        return columnMeta;
    }

    public Class<?> getFieldType() {
        return field.getType();
    }

    public boolean isId() {
        return isId;
    }

    private String initFieldName(Column column, Field field) {
        if (column != null && StringUtils.isNotBlank(column.name())) {
            return column.name();
        }

        return field.getName();
    }
}
