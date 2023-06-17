package jdbc;

import jdbc.exception.RowMapException;
import persistence.sql.view.ColumnFields;
import persistence.sql.view.ColumnName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperImpl<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public RowMapperImpl(Class<T> clazz) {this.clazz = clazz;}

    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            final T object = clazz.getDeclaredConstructor().newInstance();
            ColumnFields.from(clazz).stream().forEach(
                    field -> setField(
                            field, object, resultSet
                    )
            );
            return object;
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RowMapException(e);
        }
    }

    private void setField(Field field, T object, ResultSet resultSet) {
        field.setAccessible(true);
        try {
            field.set(object, resultSet.getObject(
                    new ColumnName(field).toString()
            ));
        } catch (IllegalAccessException | SQLException e) {
            throw new RowMapException(e);
        }
    }
}
