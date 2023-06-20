package jdbc;

import jakarta.persistence.Transient;
import persistence.sql.base.ColumnName;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class RowMapperImpl<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public RowMapperImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        T entity = getEntity();
        Field[] declaredFields = clazz.getDeclaredFields();

        Arrays.stream(declaredFields).forEach(field -> setField(field, entity, resultSet));
        return entity;
    }

    private T getEntity() {
        T entity;
        try {
            entity = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    private void setField(Field field, T entity, ResultSet resultSet) {
        if (field.isAnnotationPresent(Transient.class)) {
            return;
        }

        field.setAccessible(true);
        try {
            String columnName = new ColumnName(field).name();
            field.set(entity, resultSet.getObject(columnName));
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
