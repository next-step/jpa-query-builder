package persistence.ddl.database;

import domain.Person;
import jdbc.JdbcTemplate;

import java.util.List;

public class PersonDatabase implements Database<Person> {

    private final JdbcTemplate jdbcTemplate;
    private final ReflectiveRowMapper<Person> reflectiveRowMapper;

    public PersonDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reflectiveRowMapper = new ReflectiveRowMapper<>(Person.class);
    }

    @Override
    public Person executeQuery(String sql) {
        return jdbcTemplate.queryForObject(sql, reflectiveRowMapper);
    }

    @Override
    public List<Person> query(String sql) {
        return jdbcTemplate.query(sql, reflectiveRowMapper);
    }

    @Override
    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }
}
