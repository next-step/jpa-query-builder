package persistence.sql.entity;

import jdbc.RowMapper;
import persistence.sql.model.EntityColumnName;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class EntityRowMapper<T> implements RowMapper<T> {


    private final Class<T> clazz;

    public EntityRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }


    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            Object object = makeObject();
            setFields(object, resultSet);
            return clazz.cast(object);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Object makeObject()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> defaultConstructor = clazz.getConstructor();
        defaultConstructor.setAccessible(true);
        return defaultConstructor.newInstance();
    }

    private void setFields(Object object, ResultSet resultSet) {
        EntityFields entityFields = new EntityFields(clazz);
        entityFields.getPersistentFields().stream()
                        .forEach(field -> setField(field, object, resultSet));
    }

    private void setField(Field field, Object object, ResultSet resultSet) {
        try {
            field.setAccessible(true);
            field.set(object, resultSet.getObject(new EntityColumnName(field).getValue()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
