package database;

import jdbc.JdbcTemplate;

import java.util.HashMap;

public class SimpleDatabase implements Database {

    private final JdbcTemplate jdbcTemplate;

    public SimpleDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }

    @Override
    public HashMap<String, Object> executeQueryForObject(String sql) {
        return jdbcTemplate.queryForObject(sql, new ObjectRowMapper());
    }
}
