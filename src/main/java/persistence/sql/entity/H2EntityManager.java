package persistence.sql.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.DmlQueryBuilder;

public class H2EntityManager implements EntityManager {
    private final JdbcTemplate jdbcTemplate;
    private final DmlQueryBuilder dmlQueryBuilder;

    public H2EntityManager(final JdbcTemplate jdbcTemplate, final DmlQueryBuilder dmlQueryBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.dmlQueryBuilder = dmlQueryBuilder;
    }

    @Override
    public void persist(final Object entity) {
        final String insert = dmlQueryBuilder.insert(entity.getClass(), entity);
        jdbcTemplate.execute(insert);
    }

    @Override
    public <T> T find(final Class<T> clazz, final Long Id) {
        throw new UnsupportedOperationException();
    }
}
