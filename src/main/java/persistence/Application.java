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

            final Person person = new Person("Jaden", 30, "test@email.com", 1);

            final CreateQueryBuilder createQueryBuilder = new CreateQueryBuilder(Person.class);
            jdbcTemplate.execute(createQueryBuilder.build());

            final InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder(person);
            jdbcTemplate.execute(insertQueryBuilder.build());

            final DropQueryBuilder dropQueryBuilder = new DropQueryBuilder(Person.class);
            jdbcTemplate.execute(dropQueryBuilder.build());

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
