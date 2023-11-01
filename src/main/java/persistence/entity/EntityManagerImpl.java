package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.core.GenericRowMapper;
import persistence.dialect.H2Dialect;
import persistence.sql.Query;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

public class EntityManagerImpl implements EntityManager {

    private final JdbcTemplate jdbcTemplate;
    H2Dialect h2Dialect = new H2Dialect();
    private final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(h2Dialect);
    private final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(h2Dialect);
    private final DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(h2Dialect);

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        Query query = selectQueryBuilder.findById(clazz, id);
        return jdbcTemplate.queryForObject(query.getQuery().toString(), new GenericRowMapper<>(clazz));
    }

    @Override
    public void persist(Object entity) {
        Query query = insertQueryBuilder.queryForObject(entity);
        jdbcTemplate.execute(query.getQuery().toString());
    }

    @Override
    public void remove(Object entity) throws NoSuchFieldException, IllegalAccessException {
        Query query = deleteQueryBuilder.queryForObject(entity);
        jdbcTemplate.execute(query.getQuery().toString());
    }
}
