package persistence.entity;

import java.util.List;
import persistence.mapper.RowMapper;

public interface JdbcTemplate {
    void execute(final String sql);

    <T> T queryForObject(final String sql, final RowMapper<T> rowMapper);

    <T> List<T> query(final String sql, final RowMapper<T> rowMapper);
}
