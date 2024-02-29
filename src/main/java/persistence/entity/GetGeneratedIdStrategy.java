package persistence.entity;

import jdbc.JdbcTemplate;

public interface GetGeneratedIdStrategy {
    Long getGeneratedId(JdbcTemplate jdbcTemplate);
}
