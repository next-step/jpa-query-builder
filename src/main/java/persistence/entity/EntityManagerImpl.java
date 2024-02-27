package persistence.entity;

import jdbc.GenericRowMapper;
import jdbc.JdbcTemplate;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

public class EntityManagerImpl implements EntityManager {

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder(clazz, Id);
        return jdbcTemplate.queryForObject(selectQueryBuilder.build(), resultSet -> new GenericRowMapper<T>(clazz).mapRow(resultSet));
    }

    @Override
    public void persist(Object entity) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(entity);
        jdbcTemplate.execute(insertQueryBuilder.build());
    }

    @Override
    public void remove(Object entity) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(entity);
        jdbcTemplate.execute(deleteQueryBuilder.build());
    }
}
