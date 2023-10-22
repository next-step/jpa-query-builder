package jdbc;

import persistence.sql.meta.ColumnMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;

public class EntityRowMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public EntityRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) {

        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T entityInstance = constructor.newInstance();
            Arrays.stream(clazz.getDeclaredFields())
                    .forEach(field -> setFieldValue(entityInstance, resultSet, field));
            return entityInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void setFieldValue(T entityInstance, ResultSet resultSet, Field field) {
        ColumnMeta columnMeta = ColumnMeta.of(field);
        if (columnMeta.isTransient()) {
            return;
        }
        field.setAccessible(true);
        try {
            Object fieldValue = resultSet.getObject(columnMeta.getColumnName());
            field.set(entityInstance, fieldValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
