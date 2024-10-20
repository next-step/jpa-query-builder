package jdbc;

import jakarta.persistence.Transient;
import persistence.model.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class RowMapperImpl<T> implements RowMapper {
    private final Class<T> clazz;

    public RowMapperImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            T entityObject = clazz.getDeclaredConstructor().newInstance();

            Arrays.stream(clazz.getDeclaredFields())
                    .forEach(field -> mapField(resultSet, entityObject, field));

            return entityObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void mapField(ResultSet resultSet, T entityObject, Field field) {
        if (!field.isAnnotationPresent(Transient.class)) {
            field.setAccessible(true);
            try {
                Object value = resultSet.getObject(ReflectionUtil.getColumnName(field));
                field.set(entityObject, value);
            } catch (IllegalAccessException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
