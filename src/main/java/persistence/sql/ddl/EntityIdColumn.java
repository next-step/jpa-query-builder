package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.lang.reflect.Field;

public record EntityIdColumn(EntityColumn entityColumn, GenerationType generationType) {
    public static EntityIdColumn from(Field field) {
        return new EntityIdColumn(
            EntityColumn.from(field),
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
        return entityColumn.name();
    }

    public Class<?> type() {
        return entityColumn.type();
    }

    public boolean nullable() {
        return entityColumn.nullable();
    }

    public Field getField() {
        return entityColumn.field();
    }
}
