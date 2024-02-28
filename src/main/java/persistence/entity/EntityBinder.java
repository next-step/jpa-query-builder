package persistence.entity;

import persistence.sql.model.BaseColumn;

import java.lang.reflect.Field;

public class EntityBinder {

    private final Object entity;

    public EntityBinder(Object entity) {
        this.entity = entity;
    }

    public void bindValue(BaseColumn column, Object value) {
        try {
            Field field = column.getField();
            field.setAccessible(true);
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Invalid instance or value: " + entity + ' ' + value);
        }
    }

    public Object getValue(BaseColumn column) {
        try {
            Field field = column.getField();
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("This instance does not have any of the fields in that column.");
        }
    }
}
