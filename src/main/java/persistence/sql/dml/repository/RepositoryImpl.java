package persistence.sql.dml.repository;

import jdbc.JdbcTemplate;

import java.util.List;

public class RepositoryImpl<T> extends Repository<T> {

    public RepositoryImpl(JdbcTemplate jdbcTemplate, Class<T> clazz) {
        super(jdbcTemplate, clazz);
    }

    @Override
    public List<T> findAll(String sql) {
        return super.jdbcTemplate.query(sql, this::mapper);
    }
}
