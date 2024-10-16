package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.EntityManager;
import persistence.entity.EntityManagerImpl;
import persistence.sql.H2Dialect;
import persistence.sql.Person;
import persistence.sql.ddl.query.CreateQueryBuilder;
import persistence.sql.ddl.query.DropQueryBuilder;
import persistence.sql.dml.query.DeleteByIdQueryBuilder;
import persistence.sql.dml.query.InsertQueryBuilder;
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

            // create table
            create(jdbcTemplate, testClass);

            // test insert and select
            insert(jdbcTemplate, testClass);
            selectAll(jdbcTemplate, testClass);
            selectById(server, 1L);
            selectById(server, 2L);
            selectById(server, 3L);

            deleteById(jdbcTemplate, testClass, 1L);
            selectAll(jdbcTemplate, testClass);

            // drop table
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

//            jdbcTemplate.execute("select * from users");
    }

    private static void selectAll(JdbcTemplate jdbcTemplate, Class<?> testClass) {
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

    private static void selectById(DatabaseServer databaseServer, Long id) {
        final EntityManager em = new EntityManagerImpl(databaseServer);

        Person person = em.find(Person.class, id);
        logger.info("Person: {}", person);
    }

    private static void insert(JdbcTemplate jdbcTemplate, Class<?> testClass) {
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

    private static void deleteById(JdbcTemplate jdbcTemplate, Class<?> testClass, Long id) {
        DeleteByIdQueryBuilder deleteByIdQueryBuilder = new DeleteByIdQueryBuilder();
        String query = deleteByIdQueryBuilder.build(testClass, id);

        jdbcTemplate.execute(query);

        logger.info("Data deleted successfully!");
    }
}
