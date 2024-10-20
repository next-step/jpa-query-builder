package persistence.entity.impl;

import jdbc.JdbcTemplate;
import persistence.entity.EntityManager;
import persistence.entity.EntityRowMapper;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.dml.UpdateQueryBuilder;

import java.lang.reflect.Field;
import java.util.List;

public class FakeEntityManager implements EntityManager {
    final Class<?> clazz;
    private final JdbcTemplate jdbcTemplate;

    public FakeEntityManager(Class<?> clazz, JdbcTemplate jdbcTemplate) {
        this.clazz = clazz;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(clazz);
        String selectQuery = selectQueryBuilder.findById(clazz, id);
        List<T> query = jdbcTemplate.query(selectQuery, new EntityRowMapper<>(clazz));
        return query.isEmpty() ? null : clazz.cast(query.getFirst());
    }


    @Override
    public Object persist(Object entity) {
        Class<?> clazz = entity.getClass();
        try {
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(clazz);
            String insertQuery = insertQueryBuilder.insert(entity);
            jdbcTemplate.execute(insertQuery);
            return entity;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to persist entity", e);
        }
    }

    @Override
    public void remove(Object entity, Long id) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(clazz);
        String deleteQuery = deleteQueryBuilder.deleteById(clazz, id);
        jdbcTemplate.execute(deleteQuery);
    }

    @Override
    public void update(Object entity) {
        Class<?> clazz = entity.getClass();

        try {
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder(clazz);
            String updateQuery = updateQueryBuilder.update(entity);
            jdbcTemplate.execute(updateQuery);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to update entity", e);
        }
    }
}