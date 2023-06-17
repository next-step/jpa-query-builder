package persistence.ddl.database;

import domain.Person;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;

import java.sql.ResultSet;
import java.util.List;

public class PersonRepository implements Database<Person> {
    private final JdbcTemplate jdbcTemplate;

    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        ReflectiveRowMapper<Person> reflectiveRowMapper = new ReflectiveRowMapper<>(Person.class);

        return query("select * from PERSON", reflectiveRowMapper);
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
