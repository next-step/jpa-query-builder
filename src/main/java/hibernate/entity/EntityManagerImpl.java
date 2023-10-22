package hibernate.entity;

import hibernate.dml.InsertQueryBuilder;
import hibernate.dml.SelectQueryBuilder;
import jdbc.JdbcTemplate;
import jdbc.ReflectionRowMapper;

public class EntityManagerImpl implements EntityManager {

    private final JdbcTemplate jdbcTemplate;
    private final SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
    private final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(final Class<T> clazz, final Object id) {
        final String query = selectQueryBuilder.generateQuery(new EntityClass<>(clazz), id);
        return jdbcTemplate.queryForObject(query, new ReflectionRowMapper<>(clazz));
    }

    @Override
    public void persist(Object entity) {
        final String query = insertQueryBuilder.generateQuery(new EntityClass<>(entity.getClass()), entity);
        jdbcTemplate.execute(query);
    }
}
