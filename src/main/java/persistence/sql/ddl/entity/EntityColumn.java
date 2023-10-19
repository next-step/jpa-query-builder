package persistence.sql.ddl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 엔티티 컬럼 정다
 */
public class EntityColumn {

    private final Field field;
    private final String name;
    private final Class<?> type;

    public EntityColumn(Field declaredField) {
        this.field = declaredField;
        this.name = declaredField.getName();
        this.type = declaredField.getType();
    }

    /**
     * ID 컬럼인지 확인
     */
    public boolean isId() {
        return field.isAnnotationPresent(Id.class);
    }

    /**
     * `@Transient` 컬럼인지 확인
     */
    public boolean isTransient() {
        return field.isAnnotationPresent(Transient.class);
    }

    public Field getField() {
        return field;
    }

    public String getColumnName() {
        if (!field.isAnnotationPresent(Column.class) || field.getAnnotation(Column.class).name().isEmpty()) {
            return name;
        }
        return field.getAnnotation(Column.class).name();
    }

    public Class<?> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityColumn that = (EntityColumn) o;
        return Objects.equals(field, that.field) && Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, name, type);
    }

}
