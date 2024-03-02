package persistence;

import domain.Person;
import jdbc.JdbcTemplate;
import jdbc.PersonRowMapper;
import persistence.sql.dml.DMLGenerator;

public class DefaultEntityManager implements EntityManager {

    private final JdbcTemplate jdbcTemplate;
    private final DMLGenerator dmlGenerator;

    public DefaultEntityManager(JdbcTemplate jdbcTemplate, DMLGenerator dmlGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.dmlGenerator = dmlGenerator;
    }

    @Override
    public Person find(Class<Person> clazz, Long id) {
        String sql = dmlGenerator.generateFindById(id);
        return jdbcTemplate.queryForObject(sql, new PersonRowMapper());
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
