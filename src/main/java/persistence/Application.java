package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.H2Dialect;
import persistence.sql.ddl.query.CreateQueryBuilder;
import persistence.sql.Person;
import persistence.sql.ddl.query.DropQueryBuilder;
import persistence.sql.dml.query.InsertQueryBuilder;
import persistence.sql.dml.query.SelectAllQueryBuilder;
import persistence.sql.dml.query.SelectByIdQueryBuilder;

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

            // create table
            testCreate(jdbcTemplate, testClass);

            // test insert and select
            testInsert(jdbcTemplate, testClass);
            testSelect(jdbcTemplate, testClass);
            testSelectById(jdbcTemplate, testClass, 1L);
            testSelectById(jdbcTemplate, testClass, 2L);
            testSelectById(jdbcTemplate, testClass, 3L);

            // drop table
            testDrop(jdbcTemplate);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    private static void testCreate(JdbcTemplate jdbcTemplate, Class<?> testClass) {
        CreateQueryBuilder createQuery = new CreateQueryBuilder(new H2Dialect());
        jdbcTemplate.execute(createQuery.build(testClass));
    }

    private static void testDrop(JdbcTemplate jdbcTemplate) {
        DropQueryBuilder dropQuery = new DropQueryBuilder();
        String build = dropQuery.build(Person.class);
        logger.info("Drop query: {}", build);
        jdbcTemplate.execute(build);

//            jdbcTemplate.execute("select * from users");
    }

    private static void testSelect(JdbcTemplate jdbcTemplate, Class<?> testClass) {
        SelectAllQueryBuilder selectAllQuery = new SelectAllQueryBuilder();
        String query = selectAllQuery.build(testClass);

        List<Person> people = jdbcTemplate.query(query, (RowMapper) rs -> new Person(
                rs.getLong("id"),
                rs.getString("nick_name"),
                rs.getInt("old"),
                rs.getString("email"),
                1 // transient
        ));

        for (Person person : people) {
            logger.info("Person: {}", person);
        }
    }

    private static void testSelectById(JdbcTemplate jdbcTemplate, Class<?> testClass, Long id) {
        SelectByIdQueryBuilder selectByIdQueryBuilder = new SelectByIdQueryBuilder();
        String query = selectByIdQueryBuilder.build(testClass);
        query = query.replace("?", id.toString());

        List<Person> people = jdbcTemplate.query(query, (RowMapper) rs -> new Person(
                rs.getLong("id"),
                rs.getString("nick_name"),
                rs.getInt("old"),
                rs.getString("email"),
                1 // transient
        ));

        for (Person person : people) {
            logger.info("Person: {}", person);
        }
    }

    private static void testInsert(JdbcTemplate jdbcTemplate, Class<?> testClass) {
        InsertQueryBuilder insertQuery = new InsertQueryBuilder();
        Person person1 = new Person(1L, "a", 10, "aaa@gmail.com", 1);
        Person person2 = new Person(2L, "b", 20, "bbb@gmail.com", 2);
        Person person3 = new Person(3L, "c", 30, "ccc@gmail.com", 3);

        String query1 = insertQuery.build(person1);
        String query2 = insertQuery.build(person2);
        String query3 = insertQuery.build(person3);

        jdbcTemplate.execute(query1);
        jdbcTemplate.execute(query2);
        jdbcTemplate.execute(query3);

        logger.info("Data inserted successfully!");
    }
}
