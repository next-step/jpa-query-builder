package persistence.sql.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.DeleteQuery;
import persistence.sql.dml.InsertQuery;
import persistence.sql.dml.SelectQuery;
import persistence.sql.dml.UpdateQuery;

public class EntityManagerImpl implements EntityManager {
    private final JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        SelectQuery selectQuery = new SelectQuery(clazz);
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
    public void remove(Object entity) {
        DeleteQuery deleteQuery = new DeleteQuery(entity);
        jdbcTemplate.execute(deleteQuery.makeQuery());
    }

    @Override
    public void update(Object entity) {
        UpdateQuery updateQuery = new UpdateQuery(entity);
        System.out.println(updateQuery.makeQuery());
        jdbcTemplate.execute(updateQuery.makeQuery());
    }

}
