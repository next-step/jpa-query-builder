package persistence.sql.dml.builder;

import jdbc.RowMapper;

public class SelectQueryDto<T> {
    private final String sql;
    private final RowMapper<T> rowMapper;

    public SelectQueryDto(String sql, RowMapper<T> rowMapper) {
        this.sql = sql;
        this.rowMapper = rowMapper;
    }

    public String getSql() {
        return sql;
    }

    public RowMapper<T> getRowMapper() {
        return rowMapper;
    }
}
