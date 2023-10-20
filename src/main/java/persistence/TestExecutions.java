package persistence;

import jdbc.JdbcTemplate;
import persistence.entity.Person;
import persistence.sql.Query;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;

import java.util.List;

public class TestExecutions {

    private final JdbcTemplate jdbcTemplate;
    private final Query query;

    public TestExecutions(JdbcTemplate jdbcTemplate, Query query) {
        this.jdbcTemplate = jdbcTemplate;
        this.query = query;
    }

    public void execute() {
        Class<?> testEntityClass = Person.class;
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

    private void create(Class<?> entityClass) {
        CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(query);
        jdbcTemplate.execute(createQueryBuilder.getQuery(entityClass));
    }

    private void save(List<?> entities) {
        InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(query);
        for (Object testEntity : entities) {
            jdbcTemplate.execute(insertQueryBuilder.getQuery(testEntity));
        }
    }

    private void delete(Object entity) {
        DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder(query);
        jdbcTemplate.execute(deleteQueryBuilder.getQuery(entity));
    }

    private void drop(Class<?> entityClass) {
        DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(query);
        jdbcTemplate.execute(dropQueryBuilder.getQuery(entityClass));
    }

}
