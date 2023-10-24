package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.Query;

public class SimpleEntityManager implements EntityManager {

    private final Query query;
    private final JdbcTemplate jdbcTemplate;

    public SimpleEntityManager(Query query, JdbcTemplate jdbcTemplate) {
        this.query = query;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T, K> T find(Class<T> clazz, K id) {
        return jdbcTemplate.queryForObject(query.findById(clazz, id), new SimpleRowMapper<>(clazz));
    }

    @Override
    public void persist(Object entity) {
        jdbcTemplate.execute(query.insert(entity));
    }

    @Override
    public void remove(Object entity) {

    }
}
