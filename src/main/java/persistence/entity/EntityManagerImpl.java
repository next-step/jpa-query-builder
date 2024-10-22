package persistence.entity;

import jdbc.EntityRowMapper;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.dml.delete.DeleteQueryBuilder;
import persistence.sql.dml.insert.InsertQueryBuilder;
import persistence.sql.dml.select.SelectQueryBuilder;

public class EntityManagerImpl<T, U> implements EntityManager<T, U> {
    private static final Logger logger = LoggerFactory.getLogger(EntityManagerImpl.class);

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
    public void persist(T entity) {
        String insertQuery = InsertQueryBuilder.generateQuery(entity);
        jdbcTemplate.execute(insertQuery);
    }

    @Override
    public void remove(T entity) {
        String deleteQuery = DeleteQueryBuilder.generateQuery(entity.getClass(), entity);
        jdbcTemplate.execute(deleteQuery);
    }
}