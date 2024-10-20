package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.RowMapperImpl;
import persistence.sql.dml.DmlQueryBuilder;

import java.util.List;
import java.util.Map;

public class EntityManagerImpl implements EntityManager {
    private final DmlQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(DmlQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T findById(Class<T> clazz, Long id) {
        String selectQuery = queryBuilder.buildSelectQuery(clazz, List.of(Map.of("id", id)));
        return jdbcTemplate.queryForObject(selectQuery, resultSet ->
                new RowMapperImpl<>(clazz).mapRow(resultSet)
        );
    }

    @Override
    public <T> void persist(T entity) {
        jdbcTemplate.execute(queryBuilder.buildInsertQuery(entity));
    }

    @Override
    public <T> void remove(T entity) {
        jdbcTemplate.execute(queryBuilder.buildDeleteQuery(entity));
    }

    @Override
    public <T> void update(T entity) {
        jdbcTemplate.execute(queryBuilder.buildUpdateQuery(entity));
    }
}
