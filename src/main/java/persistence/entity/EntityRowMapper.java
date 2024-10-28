package persistence.entity;

import jakarta.persistence.Transient;
import jdbc.RowMapper;
import persistence.metadata.ColumnName;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import static common.AnnotationValidation.notPredicate;

public class EntityRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public EntityRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        Object object = mapObject(clazz);
        mapFields(object, resultSet);
        return clazz.cast(object);
    }

    private Object mapObject(Class<?> clazz) {
        Constructor<?> defaultConstructor = null;
        try {
            defaultConstructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        defaultConstructor.setAccessible(true);

        try {
            return defaultConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void mapFields(Object object, ResultSet resultSet) {
        Arrays.stream(object.getClass().getDeclaredFields())
                .filter(notPredicate(Transient.class))
                .forEach(field -> mapField(object, field, resultSet));
    }

    private void mapField(Object object, Field field, ResultSet resultSet) {
        try {
            field.setAccessible(true);
            field.set(object, resultSet.getObject(new ColumnName(field).name()));
        } catch (IllegalAccessException | SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            field.setAccessible(false);
        }
    }
}
