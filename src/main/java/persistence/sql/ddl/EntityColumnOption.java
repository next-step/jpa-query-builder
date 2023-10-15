package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.reflect.Field;

public class EntityColumnOption {
    private final boolean isNullable;
    private final boolean isPk;
    private GenerationType generationType;

    public EntityColumnOption(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("필드가 null이면 안됩니다.");
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

    public boolean hasGenerationType() {
        return generationType != null;
    }
}
