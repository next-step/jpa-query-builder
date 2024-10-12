package orm;

import jakarta.persistence.Column;
import orm.util.StringUtils;

import java.lang.reflect.Field;

public class TableField {

    private final Field field;
    private final String fieldName;

    private final ColumnMeta columnMeta;

    public TableField(Field field) {
        Column column = field.getAnnotation(Column.class);
        this.field = field;
        this.fieldName = initFieldName(column, field);
        this.columnMeta = ColumnMeta.from(column);
    }

    public Class<?> fieldType() {
        return field.getType();
    }

    public String getFieldName() {
        return fieldName;
    }

    private String initFieldName(Column column, Field field) {
        if (column != null && StringUtils.isNotBlank(column.name())) {
            return column.name();
        }

        return field.getName();
    }
}
