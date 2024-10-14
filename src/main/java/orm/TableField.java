package orm;

import jakarta.persistence.Column;
import orm.settings.JpaSettings;

import java.lang.reflect.Field;

public class TableField {

    private final Field field;
    private final String fieldName;
    private final ColumnMeta columnMeta;

    private final JpaSettings jpaSettings;

    public TableField(Field field, JpaSettings jpaSettings) {
        Column column = field.getAnnotation(Column.class);
        this.jpaSettings = jpaSettings;
        this.field = field;
        this.fieldName = extractFieldName(column, field);
        this.columnMeta = ColumnMeta.from(column);
    }

    public TableField(Field field) {
        this(field, JpaSettings.ofDefault());
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
        return false;
    }

    private String extractFieldName(Column column, Field field) {
        return jpaSettings.getNamingStrategy().namingColumn(column, field);
    }
}
