package repository;

import persistence.jdbc.JdbcTemplate;
import persistence.sql.QueryGenerator;

public class DDLRepository {
    private final JdbcTemplate jdbcTemplate;

    public DDLRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> void createTable(Class<T> entity) {
        final String sql = QueryGenerator.from(entity).create();
        jdbcTemplate.execute(sql);
    }

    public <T> void dropTable(Class<T> entity) {
        final String sql = QueryGenerator.from(entity).drop();
        jdbcTemplate.execute(sql);
    }
}
