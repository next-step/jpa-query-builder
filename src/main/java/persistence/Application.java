package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.Person;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);
            jdbcTemplate.execute(createQueryBuilder.build());

            Person person = new Person(1L, "John", 25, "email", 1);
            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(person);
            jdbcTemplate.execute(insertQueryBuilder.build());

            DropQueryBuilder dropQuery = new DropQueryBuilder(Person.class);
            jdbcTemplate.execute(dropQuery.build());

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
