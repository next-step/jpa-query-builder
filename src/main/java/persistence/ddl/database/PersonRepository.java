package persistence.ddl.database;

import domain.Person;
import jdbc.JdbcTemplate;

import java.sql.ResultSet;
import java.util.List;

public class PersonRepository implements Database {
    private final JdbcTemplate jdbcTemplate;

    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        ReflectiveRowMapper<Person> reflectiveRowMapper = new ReflectiveRowMapper<>(Person.class);

        return jdbcTemplate.query("select * from PERSON", reflectiveRowMapper);
    }

    @Override
    public ResultSet executeQuery(String sql) {
        return null;
    }

    @Override
    public List<ResultSet> query(String sql) {
        return null;
    }
}
