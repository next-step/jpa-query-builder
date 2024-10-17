package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.EntityManager;
import persistence.entity.EntityManagerImpl;
import persistence.entity.GenericRowMapper;
import persistence.sql.H2Dialect;
import persistence.sql.Person;
import persistence.sql.ddl.query.CreateQueryBuilder;
import persistence.sql.ddl.query.DropQueryBuilder;
import persistence.sql.dml.query.SelectAllQueryBuilder;

import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            final Class<?> testClass = Person.class;
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            final EntityManager em = new EntityManagerImpl(server);

            Person person1 = new Person(1L, "a", 10, "aaa@gmail.com", 1);
            Person person2 = new Person(2L, "b", 20, "bbb@gmail.com", 2);
            Person person3 = new Person(3L, "c", 30, "ccc@gmail.com", 3);

            // create table
            create(jdbcTemplate, testClass);

            // test insert and select
            insert(em, person1);
            insert(em, person2);
            insert(em, person3);
            selectAll(jdbcTemplate, testClass);
            select(em, 1L);
            select(em, 2L);
            select(em, 3L);

            logger.info("Remove person1");
            remove(em, person1);
            selectAll(jdbcTemplate, testClass);

            logger.info("Update person2");
            update(em, new Person(2L, "b", 25, "ddd@gmail.com", 5));
            selectAll(jdbcTemplate, testClass);
            drop(jdbcTemplate);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void create(JdbcTemplate jdbcTemplate, Class<?> testClass) {
        CreateQueryBuilder createQuery = new CreateQueryBuilder(new H2Dialect());
        jdbcTemplate.execute(createQuery.build(testClass));
    }

    private static void drop(JdbcTemplate jdbcTemplate) {
        DropQueryBuilder dropQuery = new DropQueryBuilder();
        String build = dropQuery.build(Person.class);
        logger.info("Drop query: {}", build);
        jdbcTemplate.execute(build);
    }

    private static void selectAll(JdbcTemplate jdbcTemplate, Class<?> testClass) {
        SelectAllQueryBuilder selectAllQuery = new SelectAllQueryBuilder();
        String query = selectAllQuery.build(testClass);

        List<Person> people = jdbcTemplate.query(query, new GenericRowMapper<>(Person.class));

        for (Person person : people) {
            logger.info("Person: {}", person);
        }
    }

    private static void select(EntityManager em, Object id) {
        Person person = em.find(Person.class, id);
        logger.info("Person: {}", person);
    }

    private static void insert(EntityManager em, Person person) {
        em.persist(person);
        logger.info("Data inserted successfully!");
    }

    private static void update(EntityManager em, Person person) {
        em.merge(person);
        logger.info("Data updated successfully!");
    }

    private static void remove(EntityManager em, Person person) {
        em.remove(person);
        logger.info("Data deleted successfully!");
    }
}
