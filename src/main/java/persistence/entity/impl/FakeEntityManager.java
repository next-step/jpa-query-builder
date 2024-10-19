package persistence.entity.impl;

import jdbc.JdbcTemplate;
import persistence.entity.EntityManager;
import persistence.entity.EntityRowMapper;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;
import persistence.sql.dml.UpdateQueryBuilder;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeEntityManager implements EntityManager {
    final Map<Class<?>, Map<Long, Object>> fakePersistContext = new HashMap<>();
    final Class<?> clazz;
    Long fakeId = 0L;
    private final JdbcTemplate jdbcTemplate;

    public FakeEntityManager(Class<?> clazz, JdbcTemplate jdbcTemplate) {
        this.clazz = clazz;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        Map<Long, Object> table = fakePersistContext.get(clazz);
        if (table != null && table.containsKey(id)) {
            return clazz.cast(table.get(id));
        } else  {
            SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(clazz);
            String selectQuery = selectQueryBuilder.findById(clazz, id);
            List<T> query = jdbcTemplate.query(selectQuery, new EntityRowMapper<>(clazz));
return query.isEmpty() ? null : clazz.cast(query.get(0));
        }
    }



    @Override
    public Object persist(Object entity) {
        Class<?> clazz = entity.getClass();
        fakePersistContext.putIfAbsent(clazz, new HashMap<>());
        Map<Long, Object> table = fakePersistContext.get(clazz);

        try {
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, ++fakeId);
            table.put(fakeId, entity);
            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(clazz);
            String insertQuery = insertQueryBuilder.insert(entity);
            jdbcTemplate.execute(insertQuery);
            return entity;

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to persist entity", e);
        }
    }

    @Override
    public <T> T remove(Class<T> clazz, Long id) {
        Map<Long, Object> table = fakePersistContext.get(clazz);
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(clazz);
        String deleteQuery = deleteQueryBuilder.deleteById(clazz, id);
        jdbcTemplate.execute(deleteQuery);
        if (table != null && table.containsKey(id)) {
            return clazz.cast(table.remove(id));
        }
        throw new IllegalArgumentException("Entity not found or unsupported entity type");
    }

    @Override
    public Object update(Object entity) {
        Class<?> clazz = entity.getClass();
        fakePersistContext.putIfAbsent(clazz, new HashMap<>());
        Map<Long, Object> table = fakePersistContext.get(clazz);

        try {
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            Long id = (Long) idField.get(entity);

            UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder(clazz);
            String updateQuery = updateQueryBuilder.update(entity);
            jdbcTemplate.update(updateQuery);
            table.put(id, clazz.cast(entity));

            return entity;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to update entity", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}