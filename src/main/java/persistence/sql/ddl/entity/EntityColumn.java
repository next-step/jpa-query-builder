package persistence.sql.ddl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

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
     * String 컬럼인지 확인
     */
    public boolean isString() {
        return type.equals(String.class);
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

}
