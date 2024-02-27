package persistence.entity;

import database.Database;
import persistence.sql.dml.DMLQueryBuilder;
import persistence.sql.model.Columns;
import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleEntityManger implements EntityManager {

    private final Database database;

    public SimpleEntityManger(Database database) {
        this.database = database;
    }

    @Override
    public <T> T find(Class<T> clazz, Object id) {
        Table table = new Table(clazz);
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table);

        String findByIdQuery = dmlQueryBuilder.buildFindByIdQuery(id);
        ResultSet resultSet = database.executeQuery(findByIdQuery);

        if (!hasNext(resultSet)) {
            return null;
        }

        T instance = createInstance(clazz);

        PKColumn pkColumn = table.getPKColumn();
        String pkColumnName = pkColumn.getName();
        Object pkColumnValue = getValue(resultSet, pkColumnName);
        pkColumn.setValue(instance, pkColumnValue);

        Columns columns = table.getColumns();
        columns.stream()
                .forEach(column -> {
                    String columnName = column.getName();
                    Object columnValue = getValue(resultSet, columnName);
                    column.setValue(instance, columnValue);
                });

        return instance;
    }

    private boolean hasNext(ResultSet resultSet) {
        try {
            return resultSet.next();
        } catch (SQLException ignored) {
            return false;
        }
    }

    private <T> T createInstance(Class<T> clazz) {
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getValue(ResultSet resultSet, String columnName) {
        try {
            return resultSet.getObject(columnName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T persist(T entity) {
        Class<?> clazz = entity.getClass();
        Table table = new Table(clazz);
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table);

        PKColumn pkColumn = table.getPKColumn();

        String insertQuery = dmlQueryBuilder.buildInsertQuery(entity);
        String pkColumnName = pkColumn.getName();

        Object id = database.executeUpdate(insertQuery, pkColumnName);

        pkColumn.setValue(entity, id);

        return entity;
    }

    @Override
    public void remove(Object entity) {

    }
}
