package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.RowMapperImpl;
import persistence.sql.dml.DmlQueryBuilder;

public class EntityManagerImpl implements EntityManager {
    private final DmlQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(DmlQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T findById(Class<T> clazz, Long id) {
        String selectQuery = queryBuilder.buildSelectByIdQuery(clazz, id);
        return jdbcTemplate.queryForObject(selectQuery, resultSet ->
                new RowMapperImpl<>(clazz).mapRow(resultSet)
        );
    }

    @Override
    public void persist(Object entity) {
        jdbcTemplate.execute(queryBuilder.buildInsertQuery(entity));
    }

    @Override
    public void remove(Object entity) {
        jdbcTemplate.execute(queryBuilder.buildDeleteQuery(entity));
    }

    @Override
    public void update(Object entity) {
        jdbcTemplate.execute(queryBuilder.buildUpdateQuery(entity));
    }
}
