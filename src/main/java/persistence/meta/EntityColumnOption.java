package persistence.meta;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import persistence.exception.FieldEmptyException;

public class EntityColumnOption {
    private final boolean isNullable;
    private final boolean isPk;
    private GenerationType generationType;
    public EntityColumnOption(Field field) {
        if (field == null) {
            throw new FieldEmptyException();
        }
        this.isNullable = !field.isAnnotationPresent(Column.class) || field.getAnnotation(Column.class).nullable();
        this.isPk = field.isAnnotationPresent(Id.class);
        if (isPk) {
            matchGenerationType(field.getAnnotation(GeneratedValue.class));
        }
    }

    private void matchGenerationType(GeneratedValue generatedValue) {
        if (generatedValue == null) {
            return;
        }
        generationType = generatedValue.strategy();
    }

    public boolean isNullable() {
        return isNullable;
    }

    public boolean isPk() {
        return isPk;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }

    public boolean hasGenerationValue() {
        return generationType != null;
    }
}
