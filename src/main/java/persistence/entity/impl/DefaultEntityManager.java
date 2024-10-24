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
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultEntityManager implements EntityManager {

    private final Map<Class<?>, Map<Long, Object>> entityCache;
    private final JdbcTemplate jdbcTemplate;

    public DefaultEntityManager(JdbcTemplate jdbcTemplate) {
        this.entityCache = new ConcurrentHashMap<>();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> Optional<T> find(Class<T> clazz, Long id) {
        // 캐시에서 찾기
        Map<Long, Object> entityMap = entityCache.get(clazz);
        Object cachedEntity = entityMap == null ? null : entityMap.get(id);
        if (cachedEntity != null) {
            return Optional.of(clazz.cast(cachedEntity));  // 캐시된 엔티티 반환
        }

        // DB에서 엔티티 조회
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(clazz);
        String selectQuery = selectQueryBuilder.findById(clazz, id);
        List<T> query = jdbcTemplate.query(selectQuery, new EntityRowMapper<>(clazz));

        if (query.isEmpty()) {
            return Optional.empty();  // 엔티티가 없는 경우 빈 Optional 반환
        }

        // 엔티티가 타입에 맞는지 확인하고 캐시
        T entity = query.getFirst();
        entityCache.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>()).put(id, entity);
        return Optional.of(entity);  // 조회된 엔티티 반환
    }

    @Override
    public Object persist(Object entity) {
        Class<?> clazz = entity.getClass();
        try {
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(clazz);
            String insertQuery = insertQueryBuilder.insert(entity);
            Long id = jdbcTemplate.executeInsert(insertQuery);
            entityCache.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>())
                    .put(id, entity);
            return entity;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to persist entity", e);
        }
    }

    @Override
    public void remove(Class<?> clazz, Long id) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(clazz);
        String deleteQuery = deleteQueryBuilder.deleteById(clazz, id);
        jdbcTemplate.execute(deleteQuery);

        if (entityCache.containsKey(clazz)) {
            entityCache.get(clazz).remove(id);
        }
    }

    @Override
    public void update(Object entity) {
        Class<?> clazz = entity.getClass();
        try {
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            Long id = (Long) idField.get(entity);

            UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder(clazz);
            String updateQuery = updateQueryBuilder.update(entity);
            jdbcTemplate.execute(updateQuery);
            entityCache.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>())
                    .put(id, entity);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to update entity", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}