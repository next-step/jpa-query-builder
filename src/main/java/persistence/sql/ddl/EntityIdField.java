package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public class EntityIdField {
    private EntityField field;

    private GenerationType generationType;

    public EntityIdField(EntityField field, GenerationType generationType) {
        this.field = field;
        this.generationType = generationType;
    }

    public EntityField getField() {
        return field;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }

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
}
