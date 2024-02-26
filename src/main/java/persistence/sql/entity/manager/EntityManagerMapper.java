package persistence.sql.entity.manager;

import persistence.sql.dml.exception.NotFoundIdException;

import java.lang.reflect.Field;

public class EntityManagerMapper<T> {

    private final T entity;
    private final Class<?> clazz;
    private final String columnName;

    public EntityManagerMapper(T entity,
                               Class<?> clazz,
                               String columnName) {
        this.entity = entity;
        this.clazz = clazz;
        this.columnName = columnName;
    }

    public String getFieldValue() {
        try {
            Field field = clazz.getDeclaredField(columnName);
            field.setAccessible(true);
            return field.get(entity).toString();
        } catch (Exception e) {
            throw new NotFoundIdException();
        }
    }
}
