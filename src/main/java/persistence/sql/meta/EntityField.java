package persistence.sql.meta;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Objects;

public class EntityField {
    private final Field field;

    public EntityField(Field field) {
        this.field = field;
    }

    public String getColumnName() {
        final jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);
        if (Objects.nonNull(column) && Objects.nonNull(column.name()) && !column.name().isBlank()) {
            return column.name();
        }
        return field.getName();
    }

    public String getDbType() {
        final FieldType fieldType = FieldType.valueOf(field);
        return fieldType.getDbType();
    }

    public boolean isQuotesNeeded() {
        final FieldType fieldType = FieldType.valueOf(field);
        return fieldType.isQuotesNeeded();
    }

    public boolean isGeneration() {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (Objects.isNull(generatedValue)) {
            return false;
        }
        return generatedValue.strategy() == GenerationType.IDENTITY;
    }

    public boolean isNotNull() {
        final jakarta.persistence.Column column = field.getAnnotation(jakarta.persistence.Column.class);
        if (Objects.isNull(column)) {
            return false;
        }
        return !column.nullable();
    }

    public boolean isId() {
        return field.isAnnotationPresent(Id.class);
    }

    public boolean isTransient() {
        return field.isAnnotationPresent(Transient.class);
    }
}
