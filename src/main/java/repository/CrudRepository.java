package repository;


import java.sql.ResultSet;
import java.util.List;
import jdbc.EntityRowsMapper;
import jdbc.EntitySingleMapper;
import jdbc.JdbcTemplate;
import persistence.sql.QueryGenerator;


public class CrudRepository {
    private final JdbcTemplate jdbcTemplate;

    public CrudRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> void save(Class<T> tClass, T entity) {
        final String query = QueryGenerator.from(tClass).insert(entity);
        jdbcTemplate.execute(query);
    }

    public <T> List<T> findAll(Class<T> tClass) {
        String query = QueryGenerator.from(tClass)
                .select()
                .findAll();

        return jdbcTemplate.query(query,
                (ResultSet rs) ->  new EntityRowsMapper<>(tClass).mapRow(rs));
    }

    public <T> T findById(Class<T> tClass, Object id) {
        String query = QueryGenerator.from(tClass)
                .select()
                .findById(id);

        return jdbcTemplate.queryForObject(query,
                (ResultSet rs) -> new EntitySingleMapper<>(tClass).mapRow(rs));
    }

}
