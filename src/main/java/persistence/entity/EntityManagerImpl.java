package persistence.entity;

import jdbc.EntityRowMapper;
import jdbc.JdbcTemplate;
import persistence.sql.dml.insert.InsertQueryBuilder;
import persistence.sql.dml.select.SelectQueryBuilder;

public class EntityManagerImpl<T, U> implements EntityManager<T, U> {
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public T find(Class<T> clazz, U id) {
        String findByIdQuery = SelectQueryBuilder.generateQuery(clazz, String.valueOf(id));
        return jdbcTemplate.queryForObject(findByIdQuery, new EntityRowMapper<>(clazz));
    }

    @Override
    public void persist(T entity) throws IllegalAccessException {
        String insertQuery = InsertQueryBuilder.generateQuery(entity);
        jdbcTemplate.execute(insertQuery);
    }

    @Override
    public void remove(T entity) {
        /* TODO */
        throw new RuntimeException();
    }
}
