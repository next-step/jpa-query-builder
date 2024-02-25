package persistence.sql.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.QueryBuilder;

import static persistence.sql.entity.RowMapperFactory.createRowMapper;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(final Class<T> clazz, final Long Id) {
        final QueryBuilder queryBuilder = new QueryBuilder(clazz, new H2Dialect());
        final String findByIdQuery = queryBuilder.createFindByIdQuery(Id);

        return jdbcTemplate.queryForObject(findByIdQuery, createRowMapper(clazz));
    }

    @Override
    public void persist(final Object entity) {
        final QueryBuilder queryBuilder = new QueryBuilder(entity.getClass(), new H2Dialect());
        final String insertQuery = queryBuilder.createInsertQuery(entity);

        jdbcTemplate.execute(insertQuery);
    }

    @Override
    public void remove(final Object entity) {
        final QueryBuilder queryBuilder = new QueryBuilder(entity.getClass(), new H2Dialect());
        final String deleteQuery = queryBuilder.createDeleteQuery(entity);

        jdbcTemplate.execute(deleteQuery);
    }
}
