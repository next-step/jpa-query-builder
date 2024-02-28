package database;

import jdbc.JdbcTemplate;
import persistence.entity.EntityBinder;
import persistence.sql.model.BaseColumn;
import persistence.sql.model.Columns;
import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class SimpleDatabase implements Database {

    private final JdbcTemplate jdbcTemplate;

    public SimpleDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }

    @Override
    public <T> T executeQueryForObject(Class<T> clazz, String sql) {
        HashMap<String, Object> queryResult = jdbcTemplate.queryForObject(sql, new ObjectRowMapper());
        return mappingEntity(clazz, queryResult);
    }

    private <T> T mappingEntity(Class<T> clazz, HashMap<String, Object> result) {
        T instance = createInstance(clazz);

        EntityBinder entityBinder = new EntityBinder(instance);
        Table table = new Table(clazz);

        PKColumn pkColumn = table.getPKColumn();
        bindColumnValue(result, pkColumn, entityBinder);

        Columns columns = table.getColumns();
        columns.stream()
                .forEach(column -> bindColumnValue(result, column, entityBinder));

        return instance;
    }

    private static void bindColumnValue(HashMap<String, Object> result, BaseColumn column, EntityBinder entityBinder) {
        String columnName = column.getName();
        Object columnValue = result.get(columnName);
        entityBinder.bindValue(column, columnValue);
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
}
