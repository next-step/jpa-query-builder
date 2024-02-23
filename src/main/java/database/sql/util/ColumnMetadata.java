package database.sql.util;

import database.sql.util.column.EntityColumn;
import database.sql.util.column.FieldToEntityColumnConverter;
import database.sql.util.type.TypeConverter;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class ColumnMetadata {

    private final Field field;
    private final EntityColumn entityColumn;

    public ColumnMetadata(Field field) {
        this.field = field;
        this.entityColumn = convertFieldToEntityColumn(field);

    }

    public String getColumnName() {
        return entityColumn.getColumnName();
    }

    public String toColumnDefinition(TypeConverter typeConverter) {
        return entityColumn.toColumnDefinition(typeConverter);
    }

    public boolean isPrimaryKeyField() {
        return field.isAnnotationPresent(Id.class);
    }

    private EntityColumn convertFieldToEntityColumn(Field field) {
        return new FieldToEntityColumnConverter(field).convert();
    }

    public Field getField() {
        return field;
    }
}
