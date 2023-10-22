package persistence.sql.meta;

import jakarta.persistence.*;
import persistence.sql.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public class ColumnMeta {

    private final Field field;

    private ColumnMeta(Field field) {
        this.field = field;
    }

    public static ColumnMeta of(Field field) {
        return new ColumnMeta(field);
    }

    public boolean isId() {
        return field.isAnnotationPresent(Id.class);
    }

    public boolean isTransient() {
        return field.isAnnotationPresent(Transient.class);
    }

    public boolean isGenerationTypeIdentity() {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return false;
        }
        GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);
        return generatedValue.strategy() == GenerationType.IDENTITY;
    }

    public boolean isNullable() {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        return columnAnnotation == null || columnAnnotation.nullable();
    }

    public String getColumnName() {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null || StringUtils.isNullOrEmpty(columnAnnotation.name())) {
            return field.getName().toLowerCase();
        }
        return columnAnnotation.name();
    }

    public Class<?> getJavaType() {
        return field.getType();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ColumnMeta that = (ColumnMeta) object;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }
}
