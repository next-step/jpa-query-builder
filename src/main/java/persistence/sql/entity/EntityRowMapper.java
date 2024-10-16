package persistence.sql.entity;

import jakarta.persistence.Transient;
import jdbc.RowMapper;
import persistence.sql.Metadata;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public EntityRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Metadata metadata = new Metadata(clazz);
        T entity = clazz.getDeclaredConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Transient.class)) {
                field.setAccessible(true);
                field.set(entity, resultSet.getObject(metadata.getFieldName(field)));
            }
        }
        return entity;

    }
}
