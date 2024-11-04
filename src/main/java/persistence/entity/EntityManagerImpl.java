package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.DmlQueryBuilder;

public class EntityManagerImpl<T> implements EntityManager<T> {

    private final DmlQueryBuilder dmlQueryBuilder;
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.dmlQueryBuilder = new DmlQueryBuilder();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public T find(Class<T> clazz, Long id) {
        String query = dmlQueryBuilder.buildSelectByIdQuery(clazz, id);
        return jdbcTemplate.queryForObject(query, new DefaultRowMapper<>(clazz));
    }

    @Override
    public T persist(T entity) {
        String query = dmlQueryBuilder.buildInsertQuery(entity);
        jdbcTemplate.execute(query);
        return entity;
    }

    @Override
    public void remove(T entity) {
        String query = dmlQueryBuilder.buildDeleteQuery(entity);
        jdbcTemplate.execute(query);
    }

    @Override
    public void update(T entity) {
        String query = dmlQueryBuilder.buildUpdateQuery(entity);
        jdbcTemplate.execute(query);
    }
}
