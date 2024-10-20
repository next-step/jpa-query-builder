package persistence.sql.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.InsertQuery;
import persistence.sql.dml.SelectQueryBuilder;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        SelectQueryBuilder selectQuery = new SelectQueryBuilder(clazz);
        selectQuery.findById(id);
        return jdbcTemplate.queryForObject(selectQuery.findById(id), new EntityRowMapper<>(clazz));
    }

    @Override
    public void persist(Object entity) {
        InsertQuery insertQuery = new InsertQuery(entity);
        insertQuery.makeQuery();
        jdbcTemplate.execute(insertQuery.makeQuery());
    }

    @Override
    public void remove(Object Entity) {

    }

}
