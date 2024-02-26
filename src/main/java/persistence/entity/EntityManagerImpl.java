package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.RowMapperImpl;
import persistence.sql.dml.builder.SelectQueryBuilder;

public class EntityManagerImpl<T> implements EntityManager<T> {

    private final JdbcTemplate jdbcTemplate;
    private final SelectQueryBuilder selectQueryBuilder;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.selectQueryBuilder = new SelectQueryBuilder();
    }

    @Override
    public T find(Class<T> clazz, Long id) {
        return jdbcTemplate.queryForObject(selectQueryBuilder.findAll(clazz), new RowMapperImpl<>(clazz));
    }

    @Override
    public T persist(T entity) {
        return null;
    }

    @Override
    public void remove(T entity) {

    }
}
