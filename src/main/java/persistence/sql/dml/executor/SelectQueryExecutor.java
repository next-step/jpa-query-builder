package persistence.sql.dml.executor;

import jdbc.JdbcTemplate;

import java.sql.ResultSet;

public class SelectQueryExecutor {

    private final JdbcTemplate jdbcTemplate;

    public SelectQueryExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    ResultSet executeQuery(String sql) {
        return null;
    }

}
