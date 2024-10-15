package persistence.sql.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.SelectQueryBuilder;

import java.sql.Connection;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(Connection connection) {
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        String selectQuery = new SelectQueryBuilder(clazz).findById(clazz, id);
        try {
            return jdbcTemplate.queryForObject(selectQuery, new EntityRowMapper<>(clazz));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
