package jdbc;

import jakarta.persistence.Transient;
import persistence.sql.NameUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityRowMapper<T> implements RowMapper<T> {
    private final Class<T> entityClass;

    public EntityRowMapper(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        T entity = this.entityClass.getDeclaredConstructor().newInstance();

        for (Field field : this.entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            field.setAccessible(true);
            Object object = resultSet.getObject(NameUtils.getColumnName(field), field.getType());
            field.set(entity, object);
        }
        return entity;
    }
}
