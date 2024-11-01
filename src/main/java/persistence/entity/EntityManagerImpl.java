package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.DmlQueryBuilder;

public class EntityManagerImpl<T> implements EntityManager<T> {

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public T find(Class<T> clazz, Long id) {
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        String query = dmlQueryBuilder.buildSelectByIdQuery(clazz, id);
        return jdbcTemplate.queryForObject(query, new DefaultRowMapper<>(clazz));
    }

    @Override
    public T persist(T entity) {
        DmlQueryBuilder dmlQueryBuilder = new DmlQueryBuilder();
        String query = dmlQueryBuilder.buildInsertQuery(entity);
        jdbcTemplate.execute(query);
        return entity;
    }

    @Override
    public void remove(Object entity) {

    }
}
