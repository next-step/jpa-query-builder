package persistence.ddl.database;

import domain.Person;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;

import java.sql.ResultSet;
import java.util.List;

public class PersonDatabase implements Database<Person> {

    private final JdbcTemplate jdbcTemplate;

    public PersonDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ResultSet executeQuery(String sql) {
        return null;
    }

    @Override
    public List<Person> query(String sql, RowMapper<Person> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper);
    }
}
