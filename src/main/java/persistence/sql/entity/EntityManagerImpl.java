package persistence.sql.entity;

import jakarta.persistence.Id;
import jdbc.JdbcTemplate;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

import java.lang.reflect.Field;
import java.sql.Connection;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        String selectQuery = new SelectQueryBuilder(clazz).findById(clazz, id);
        return jdbcTemplate.queryForObject(selectQuery, new EntityRowMapper<>(clazz));
    }

    @Override
    public Object persist(Object entity) {
        Class<?> clazz = entity.getClass();
        String insertQuery = new InsertQueryBuilder(clazz).getInsertQuery(entity);
        jdbcTemplate.execute(insertQuery);
        return entity;
    }

    @Override
    public void remove(Object entity) {
        Class<?> clazz = entity.getClass();

        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
        String deleteQuery = deleteQueryBuilder.delete(clazz, getIdValue(entity));

        jdbcTemplate.execute(deleteQuery);
    }

    private Object getIdValue(Object entity) {
        Class<?> clazz = entity.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    return field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("id값이 없음");
                }
            }
        }

        return null;
    }
}
