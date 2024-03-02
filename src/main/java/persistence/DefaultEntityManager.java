package persistence;

import domain.Person;
import jdbc.JdbcTemplate;
import jdbc.PersonRowMapper;
import persistence.sql.dml.DMLGenerator;

import java.util.List;

public class DefaultEntityManager implements EntityManager {

    private final JdbcTemplate jdbcTemplate;
    private final DMLGenerator dmlGenerator;

    public DefaultEntityManager(JdbcTemplate jdbcTemplate, DMLGenerator dmlGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.dmlGenerator = dmlGenerator;
    }

    @Override
    public Person find(Class<Person> clazz, Long id) {
        if (id == null) {
            throw new IllegalArgumentException("[EntityManager] find: id is null");
        }

        String sql = dmlGenerator.generateFindById(id);
        List<Person> people = jdbcTemplate.query(sql, new PersonRowMapper());

        if (people.isEmpty()) {
            return null;
        }

        return people.get(0);
    }

    @Override
    public Object persist(Object entity) {
        return null;
    }

    @Override
    public void remove(Object entity) {

    }
}
