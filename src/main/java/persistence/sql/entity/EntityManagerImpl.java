package persistence.sql.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.QueryBuilder;
import persistence.sql.dml.domain.Person;
import persistence.sql.dml.keygenerator.H2KeyGenerator;
import persistence.sql.dml.keygenerator.KeyGenerator;

import static persistence.sql.entity.RowMapperFactory.createRowMapper;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(final Class<T> clazz, final Long Id) {
        final QueryBuilder queryBuilder = new QueryBuilder(clazz);
        final String findByIdQuery = queryBuilder.createFindByIdQuery(Id);

        return jdbcTemplate.queryForObject(findByIdQuery, createRowMapper(clazz));
    }

    @Override
    public Object persist(final Object entity) {
        return null;
    }

    @Override
    public void remove(final Object entity) {

    }
}
