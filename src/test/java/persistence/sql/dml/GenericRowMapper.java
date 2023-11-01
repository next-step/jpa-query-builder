package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jdbc.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericRowMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public GenericRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance = clazz.getDeclaredConstructor().newInstance();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Transient declaredAnnotation = declaredField.getDeclaredAnnotation(Transient.class);
            String columnName = getColumnName(declaredField);
            if(columnName != null && declaredAnnotation == null) {
                declaredField.setAccessible(Boolean.TRUE);
                Object object = resultSet.getObject(columnName);
                declaredField.set(instance, object);
            }
        }
        return instance;
    }

    private String getColumnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        return (column != null && !column.name().isEmpty()) ? column.name() : field.getName();
    }
}
