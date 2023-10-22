package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.ResultMapper;
import jdbc.RowMapper;
import persistence.sql.dml.QueryDml;
import persistence.sql.dml.SelectQuery;

import java.sql.Connection;
import java.util.List;

public abstract class EntityManagerImpl<T> implements EntityManager<T> {
    private final RowMapper<T> rowMapper;
    private JdbcTemplate jdbcTemplate;

    public EntityManagerImpl(Connection connection, Class<T> tClass) {
        this.rowMapper = new ResultMapper<>(tClass);
        this.jdbcTemplate = new JdbcTemplate(connection);
    }

    @Override
    public List<T> findAll() {
        String query = SelectQuery.create(rowMapper.getClass(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public <R> T findById(R r) {
        String query = SelectQuery.create(rowMapper.getClass(), new Object() {
        }.getClass().getEnclosingMethod().getName());

        return jdbcTemplate.queryForObject(query, rowMapper);
    }

    @Override
    public <T> T find(Class<T> clazz, Long Id) {
        String query = SelectQuery.create(clazz, "findById", Id);
        return (T) jdbcTemplate.queryForObject(query, rowMapper);
    }

    @Override
    public Object persist(Object entity) {
        jdbcTemplate.execute(QueryDml.insert(entity));
        // TODO: 반환값이 정확히 무엇인지 확인 할 필요가 있음
        return entity;
    }

    @Override
    public void remove(Object entity) {
        jdbcTemplate.execute(QueryDml.delete(entity));
    }
}
