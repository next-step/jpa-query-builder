package persistence.sql.dml;

import java.util.List;
import jdbc.JdbcTemplate;

public class JpaRepositoryImpl<T, ID> implements JpaRepository<T, ID> {

    private final Class<T> entityClass;
    private final JdbcTemplate jdbcTemplate;

    public JpaRepositoryImpl(Class<T> entityClass, JdbcTemplate jdbcTemplate) {
        this.entityClass = entityClass;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(T entity) throws IllegalAccessException {
        String sql = new InsertQuery<>(entity).generateQuery();
        jdbcTemplate.execute(sql);
    }

    @Override
    public T findById(ID id) {
        String sql = new FindByIdQuery<>(entityClass, id).generateQuery();
        return jdbcTemplate.queryForObject(sql, new GenericRowMapper<>(entityClass));
    }

    @Override
    public List<T> findAll() {
        String sql = new FindAllQuery(entityClass).generateQuery();
        return jdbcTemplate.query(sql, new GenericRowMapper<>(entityClass));
    }

    @Override
    public void deleteById(ID id) {
        String sql = new DeleteByIdQuery<>(entityClass, id).generateQuery();
        jdbcTemplate.execute(sql);
    }

    @Override
    public void delete(T entity) {
        String sql = new DeleteQuery<>(entity).generateQuery();
        jdbcTemplate.execute(sql);
    }

    @Override
    public void deleteAll() {
        String sql = new DeleteAllQuery(entityClass).generateQuery();
        jdbcTemplate.execute(sql);
    }

}
