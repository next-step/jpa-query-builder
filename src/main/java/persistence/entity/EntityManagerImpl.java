package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.RowMapperImpl;
import persistence.sql.dml.builder.DeleteQueryBuilder;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.builder.SelectQueryBuilder;

public class EntityManagerImpl<T> implements EntityManager<T> {

    private final JdbcTemplate jdbcTemplate;
    private final SelectQueryBuilder selectQueryBuilder;
    private final InsertQueryBuilder insertQueryBuilder;
    private final DeleteQueryBuilder deleteQueryBuilder;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.selectQueryBuilder = new SelectQueryBuilder();
        this.insertQueryBuilder = new InsertQueryBuilder();
        this.deleteQueryBuilder = new DeleteQueryBuilder();
    }

    @Override
    public T find(Class<T> clazz, Long id) {
        return jdbcTemplate.queryForObject(selectQueryBuilder.findAll(clazz), new RowMapperImpl<>(clazz));
    }

    @Override
    public void persist(T entity) {
        jdbcTemplate.execute(insertQueryBuilder.generateSQL(entity));
    }

    @Override
    public void remove(T entity) {
        jdbcTemplate.execute(deleteQueryBuilder.generateSQL(entity));
    }
}
