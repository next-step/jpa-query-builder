package database;

import jdbc.RowMapper;
import persistence.entity.EntityBinder;
import persistence.sql.model.BaseColumn;
import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityRowMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public EntityRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        T instance = createInstance(clazz);
        EntityBinder entityBinder = new EntityBinder(instance);

        Table table = new Table(clazz);

        PKColumn pkColumn = table.getPKColumn();
        bindColumnValue(resultSet, pkColumn, entityBinder);

        table.getColumns()
                .stream()
                .forEach(column -> bindColumnValue(resultSet, column, entityBinder));

        return instance;
    }

    private T createInstance(Class<T> clazz) {
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void bindColumnValue(ResultSet result, BaseColumn column, EntityBinder entityBinder) {
        try {
            String columnName = column.getName();
            Object columnValue = result.getObject(columnName);
            entityBinder.bindValue(column, columnValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
