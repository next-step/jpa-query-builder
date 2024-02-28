package persistence.entity;

import database.Database;
import persistence.sql.dml.DMLQueryBuilder;
import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

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
        HashMap<String, Object> queryResult = database.executeQueryForObject(findByIdQuery);

        T instance = createInstance(clazz);

        PKColumn pkColumn = table.getPKColumn();
        String pkColumnName = pkColumn.getName();
        Object pkColumnValue = queryResult.get(pkColumnName);
        pkColumn.setValue(instance, pkColumnValue);

        table.getColumns()
                .stream()
                .forEach(column -> {
                    String columnName = column.getName();
                    Object columnValue = queryResult.get(columnName);
                    column.setValue(instance, columnValue);
                });

        return instance;
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

    @Override
    public void persist(Object entity) {
        Class<?> clazz = entity.getClass();
        Table table = new Table(clazz);
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table);

        String insertQuery = dmlQueryBuilder.buildInsertQuery(entity);
        database.execute(insertQuery);
    }

    @Override
    public void remove(Object entity) {
        Class<?> clazz = entity.getClass();
        Table table = new Table(clazz);
        DMLQueryBuilder dmlQueryBuilder = new DMLQueryBuilder(table);

        PKColumn pkColumn = table.getPKColumn();
        Object pkColumnValue = pkColumn.getValue(entity);

        String deleteByIdQuery = dmlQueryBuilder.buildDeleteByIdQuery(pkColumnValue);
        database.execute(deleteByIdQuery);
    }
}
