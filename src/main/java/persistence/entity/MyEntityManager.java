package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
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
    public void persist(Object entity) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
        String query = insertQueryBuilder.build(entity);
        jdbcTemplate.execute(query);
    }

    @Override
    public void remove(Object entity) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
        String query = deleteQueryBuilder.build(entity);
        jdbcTemplate.execute(query);
    }
}
