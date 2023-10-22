package persistence;

import jdbc.JdbcTemplate;
import persistence.entity.Person;
import persistence.sql.Query;
import persistence.sql.dialect.h2.H2Query;

import java.util.List;

public class Executions {

    private final JdbcTemplate jdbcTemplate;
    private final Query<Person, Long> query = new H2Query<>();

    public Executions(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void execute() {
        Class<Person> testEntityClass = Person.class;
        List<Person> testEntities = makeTestEntities();

        // create
        create(testEntityClass);
        // save
        save(testEntities);

        // delete
        delete(testEntities.get(1));

        // drop
//        drop(testEntityClass);
    }

    private List<Person> makeTestEntities() {
        return List.of(
                new Person("test1", 10, "test1@gmail.com", 0),
                new Person("test2", 11, "test2@gmail.com", 1));
    }

    private void create(Class<Person> entityClass) {
        jdbcTemplate.execute(query.create(entityClass));
    }

    private void save(List<Person> entities) {
        for (Person testEntity : entities) {
            jdbcTemplate.execute(query.insert(testEntity));
        }
    }

    private void delete(Person entity) {
        jdbcTemplate.execute(query.delete(entity));
    }

    private void drop(Class<Person> entityClass) {
        jdbcTemplate.execute(query.drop(entityClass));
    }

}
