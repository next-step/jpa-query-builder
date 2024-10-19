package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import orm.dsl.QueryBuilder;
import persistence.sql.ddl.Person;

import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            QueryBuilder queryBuilder = new QueryBuilder();
            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            final String ddl = queryBuilder
                    .createTable(Person.class)
                    .ifNotExist()
                    .extractSql();

            final List<Person> newPeople = List.of(
                    new Person(1L, 20, "설동민 1"),
                    new Person(2L, 25, "설동민 2"),
                    new Person(3L, 30, "설동민 3")
            );

            String insertQuery = queryBuilder.insertInto(Person.class)
                    .values(newPeople)
                    .extractSql();

            List<Person> peopleList = queryBuilder.selectFrom(Person.class)
                    .fetch();

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
