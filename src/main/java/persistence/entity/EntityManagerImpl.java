package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.query.DeleteByIdQueryBuilder;
import persistence.sql.dml.query.InsertQueryBuilder;
import persistence.sql.dml.query.SelectByIdQueryBuilder;
import persistence.sql.dml.query.UpdateQueryBuilder;

public class EntityManagerImpl implements EntityManager {

    private static final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
    private static final SelectByIdQueryBuilder selectByIdQueryBuilder = new SelectByIdQueryBuilder();
    private static final DeleteByIdQueryBuilder deleteByIdQueryBuilder = new DeleteByIdQueryBuilder();
    private static final UpdateQueryBuilder updateQueryBuilder = new UpdateQueryBuilder();

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Object Id) {
        final String query = selectByIdQueryBuilder.build(clazz, Id);
        return jdbcTemplate.queryForObject(query, new GenericRowMapper<>(clazz));
    }

    @Override
    public void persist(Object entity) {
        final String query = insertQueryBuilder.build(entity);
        jdbcTemplate.execute(query);
    }

    @Override
    public void remove(Object entity) {
        final String query = deleteByIdQueryBuilder.build(entity);
        jdbcTemplate.execute(query);
    }

    @Override
    public void update(Object entity) {
        final String query = updateQueryBuilder.build(entity);
        jdbcTemplate.execute(query);
    }
}
