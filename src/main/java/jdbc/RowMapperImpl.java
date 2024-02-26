package jdbc;

import jakarta.persistence.Transient;
import persistence.sql.meta.column.ColumnName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperImpl<T> implements RowMapper<T> {
    private final Class<T> clazz;


    public RowMapperImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        T result;
        try {
            result = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            field.setAccessible(true);
            setValue(result, field, resultSet);
        }
        return result;
    }

    private void setValue(T object, Field field, ResultSet resultSet) throws SQLException {
        try {
            String columnName = new ColumnName(field).getName();
            if (field.getType().equals(Long.class)) {
                field.set(object, resultSet.getLong(columnName));
            }
            if (field.getType().equals(Integer.class)) {
                field.set(object, resultSet.getInt(columnName));
            }
            if (field.getType().equals(String.class)) {
                field.set(object, resultSet.getString(columnName));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
