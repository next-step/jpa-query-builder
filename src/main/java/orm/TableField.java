package orm;

import jakarta.persistence.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orm.exception.CannotExtractEntityFieldValueException;
import orm.settings.JpaSettings;

import java.lang.reflect.Field;

public class TableField {

    private static final Logger log = LoggerFactory.getLogger(TableField.class);
    private final Field field;

    // @Column을 고려한 실제 필드명
    private final String fieldName;

    // @Column을 고려하지 않은 엔티티 필드들의 자바 필드명
    private final String classFieldName;

    private final ColumnMeta columnMeta;

    private final JpaSettings jpaSettings;
    private Object fieldValue;

    public <T> TableField(Field field, T entity, JpaSettings jpaSettings) {
        Column column = field.getAnnotation(Column.class);
        this.jpaSettings = jpaSettings;
        this.field = field;
        this.fieldName = extractFieldName(column, field);
        this.classFieldName = field.getName();
        this.fieldValue = extractFieldValue(field, entity);
        this.columnMeta = ColumnMeta.from(column);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getClassFieldName() {
        return classFieldName;
    }

    public ColumnMeta getColumnMeta() {
        return columnMeta;
    }

    public Class<?> getFieldType() {
        return field.getType();
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    protected void setFieldValue(Object newFieldValue) {
        this.fieldValue = newFieldValue;
    }

    public boolean isId() {
        return false;
    }

    private String extractFieldName(Column column, Field field) {
        return jpaSettings.getNamingStrategy().namingColumn(column, field);
    }

    private <T> Object extractFieldValue(Field field, T entity) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            log.error("Cannot access field: " + field.getName(), e);
            throw new CannotExtractEntityFieldValueException("Cannot extract field value: " + field.getName(), e);
        }
    }
}
