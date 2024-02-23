package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.SelectQueryBuilder;

public class MyEntityManager implements EntityManager {

    private final JdbcTemplate jdbcTemplate;

    public MyEntityManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
        String query = selectQueryBuilder.build(clazz, Id);
        return jdbcTemplate.queryForObject(query, RowMapperFactory.create(clazz));
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
