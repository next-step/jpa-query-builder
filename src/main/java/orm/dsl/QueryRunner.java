package orm.dsl;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;

import java.util.List;

public class QueryRunner {

    private final JdbcTemplate jdbcTemplate;

    public QueryRunner() {
        this.jdbcTemplate = null;
    }

    public QueryRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <E> E fetchOne(String sql, RowMapper<E> rowMapper) {
        throwIfNoJdbcTemplate();
        return jdbcTemplate.queryForObject(sql, rowMapper);
    }

    public <E> List<E> fetch(String sql, RowMapper<E> rowMapper) {
        throwIfNoJdbcTemplate();
        return jdbcTemplate.query(sql, rowMapper);
    }

    public <E> void execute(String sql) {
        throwIfNoJdbcTemplate();
        jdbcTemplate.execute(sql);
    }

    private void throwIfNoJdbcTemplate() {
        if (jdbcTemplate == null) {
            throw new IllegalStateException("JdbcTemplate is not set");
        }
    }
}
