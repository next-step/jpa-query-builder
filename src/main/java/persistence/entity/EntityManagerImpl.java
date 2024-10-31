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
        DmlQueryBuilder<T> dmlQueryBuilder = DmlQueryBuilder.from(clazz);
        String query = dmlQueryBuilder.buildSelectByIdQuery(id);
        return jdbcTemplate.queryForObject(query, new DefaultRowMapper<>(clazz));
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
