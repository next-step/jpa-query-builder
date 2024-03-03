package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.RowMapperImpl;
import persistence.sql.model.Table;
import persistence.sql.dml.builder.DeleteQueryBuilder;
import persistence.sql.dml.builder.InsertQueryBuilder;
import persistence.sql.dml.builder.SelectQueryBuilder;
import persistence.sql.dml.model.DMLColumn;

public class EntityManagerImpl implements EntityManager {

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        final String query = findQuery(clazz, id);

        return jdbcTemplate.queryForObject(query, new RowMapperImpl<>(clazz));
    }

    private <T> String findQuery(Class<T> clazz, Long id) {
        final SelectQueryBuilder queryBuilder = new SelectQueryBuilder(
                new Table(clazz), new DMLColumn(clazz)
        );

        return queryBuilder.build(clazz, id);
    }

    @Override
    public void persist(Object entity) {
        final String query = persistQuery(entity);

        jdbcTemplate.execute(query);
    }

    private String persistQuery(Object entity) {
        final InsertQueryBuilder queryBuilder = new InsertQueryBuilder(
                new Table(entity.getClass()), new DMLColumn(entity)
        );

        return queryBuilder.build(entity);
    }

    @Override
    public void remove(Object entity) {
        final String query = removeQuery(entity);

        jdbcTemplate.execute(query);
    }

    private String removeQuery(Object entity) {
        final DeleteQueryBuilder queryBuilder = new DeleteQueryBuilder(
                new Table(entity.getClass()), new DMLColumn(entity)
        );

        return queryBuilder.build();
    }

}
