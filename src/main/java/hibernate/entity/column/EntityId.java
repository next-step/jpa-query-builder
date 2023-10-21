package hibernate.entity.column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class EntityId implements EntityColumn {

    private final GenerationType generationType;
    private final EntityField entityField;

    public EntityId(final Field field) {
        if (!field.isAnnotationPresent(Id.class)) {
            throw new IllegalArgumentException("Id 어노테이션이 없습니다.");
        }
        this.generationType = parseGenerationType(field);
        this.entityField = new EntityField(field);
    }

    private static GenerationType parseGenerationType(final Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return GenerationType.AUTO;
        }
        return field.getAnnotation(GeneratedValue.class).strategy();
    }

    @Override
    public String getFieldName() {
        return entityField.getFieldName();
    }

    @Override
    public Object getFieldValue(final Object entity) {
        return entityField.getFieldValue(entity);
    }

    @Override
    public void assignFieldValue(Object instance, Object value) {
        entityField.assignFieldValue(instance, value);
    }

    @Override
    public ColumnType getColumnType() {
        return entityField.getColumnType();
    }

    @Override
    public boolean isNullable() {
        return entityField.isNullable();
    }

    @Override
    public boolean isId() {
        return true;
    }

    @Override
    public GenerationType getGenerationType() {
        return generationType;
    }
}
