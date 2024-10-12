package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public record EntityIdField(EntityField field, GenerationType generationType) {
    public static EntityIdField of(Field field) {
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        GenerationType generationType = GenerationType.AUTO;

        if (generatedValue != null) {
            generationType = generatedValue.strategy();
        }

        return new EntityIdField(
                EntityField.of(field),
                generationType
        );
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
