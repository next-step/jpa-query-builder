package database;

import jdbc.JdbcTemplate;
import persistence.entity.EntityBinder;
import persistence.sql.model.BaseColumn;
import persistence.sql.model.Columns;
import persistence.sql.model.PKColumn;
import persistence.sql.model.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    public <T> T executeQueryForObject(Class<T> clazz, String sql) {
        return jdbcTemplate.queryForObject(sql, new EntityRowMapper<>(clazz));
    }
}
