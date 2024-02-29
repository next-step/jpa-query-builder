package persistence.entity;

import jdbc.JdbcTemplate;
import jdbc.RowMapper;

public class H2GetGeneratedIdStrategy implements GetGeneratedIdStrategy {
    @Override
    public Long getGeneratedId(JdbcTemplate jdbcTemplate) {
        Long nextId = jdbcTemplate.queryForObject("CALL IDENTITY()", getNextId());
        return getCurrentId(nextId);
    }

    private RowMapper<Long> getNextId() {
        return rs -> rs.getLong(1);
    }

    private long getCurrentId(Long nextId) {
        return nextId - 1;
    }
}
