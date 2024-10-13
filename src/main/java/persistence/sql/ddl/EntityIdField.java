package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public record EntityIdField(EntityField field, GenerationType generationType) {
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
        return field.name();
    }

    public Class<?> type() {
        return field.type();
    }

    public boolean nullable() {
        return field.nullable();
    }
}
