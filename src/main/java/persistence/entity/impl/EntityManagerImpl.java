package persistence.entity.impl;

import jdbc.JdbcTemplate;
import persistence.entity.EntityManager;
import persistence.entity.EntityRowMapperFactory;
import persistence.sql.QueryTranslator;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;

    private final QueryTranslator queryBuilder;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this(jdbcTemplate, new QueryTranslator());
    }

    public EntityManagerImpl(JdbcTemplate jdbcTemplate, QueryTranslator queryBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryBuilder = queryBuilder;
    }


    @Override
    public <T> T find(Class<T> entityClass, Long id) {
        String selectByIdQuery = queryBuilder.getSelectByIdQuery(entityClass, id);

        return jdbcTemplate.queryForObject(
            selectByIdQuery,
            EntityRowMapperFactory.getInstance().getRowMapper(entityClass)
        );
    }

    @Override
    public Object persist(Object entity) {
        String insertQuery = queryBuilder.getInsertQuery(entity);

        jdbcTemplate.execute(insertQuery);

        return entity;
    }

    @Override
    public void remove(Object entity) {
        String deleteQueryFromEntity = queryBuilder.getDeleteQueryFromEntity(entity);

        jdbcTemplate.execute(deleteQueryFromEntity);
    }
}
