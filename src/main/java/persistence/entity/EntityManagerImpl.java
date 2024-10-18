package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.DmlQueryBuilder;

public class EntityManagerImpl implements EntityManager {
    private final DmlQueryBuilder queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(DmlQueryBuilder queryBuilder, JdbcTemplate jdbcTemplate) {
        this.queryBuilder = queryBuilder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        return null;
    }

    @Override
    public <T> void persist(T entity) {

    }

    @Override
    public <T> void remove(T entity) {

    }

    @Override
    public <T> void update(T entity) {

    }
}
