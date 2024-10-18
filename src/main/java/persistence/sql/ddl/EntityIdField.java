package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public record EntityIdField(EntityField entityField, GenerationType generationType) {
    public static EntityIdField from(Field field) {
        return new EntityIdField(
            EntityField.from(field),
            getGenerationType(field)
        );
    }

    private static GenerationType getGenerationType(Field field) {
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);

        if (generatedValue == null) {
            return (GenerationType) AnnotationUtils.getDefaultValue(GeneratedValue.class, "strategy");
        }

        return generatedValue.strategy();
    }

    public String name() {
        return entityField.name();
    }

    public Class<?> type() {
        return entityField.type();
    }

    public boolean nullable() {
        return entityField.nullable();
    }

    public Field getField() {
        return entityField.field();
    }
}
