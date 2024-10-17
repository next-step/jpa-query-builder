package orm.dsl;

import jdbc.JdbcTemplate;
import orm.row_mapper.DefaultRowMapper;

import java.sql.ResultSet;

public class QueryExecutor {

    private final JdbcTemplate jdbcTemplate;

    public QueryExecutor() {
        this.jdbcTemplate = null;
    }

    public QueryExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <E> E execute(String sql, Class<E> entityClass) {
        throwIfNoJdbcTemplate();
        return jdbcTemplate.queryForObject(sql, new DefaultRowMapper<>(entityClass));
    }

    private void throwIfNoJdbcTemplate() {
        if (jdbcTemplate == null) {
            throw new IllegalStateException("JdbcTemplate is not set");
        }
    }
}
