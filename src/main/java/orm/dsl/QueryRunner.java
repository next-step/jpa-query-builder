package orm.dsl;

import jdbc.JdbcTemplate;
import orm.row_mapper.DefaultRowMapper;

public class QueryRunner {

    private final JdbcTemplate jdbcTemplate;

    public QueryRunner() {
        this.jdbcTemplate = null;
    }

    public QueryRunner(JdbcTemplate jdbcTemplate) {
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
