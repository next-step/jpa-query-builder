package persistence;

import database.DatabaseServer;
import database.H2;
import persistence.sql.ddl.*;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            CreateQueryBuilder createQueryBuilder = new H2CreateQueryBuilder(Person.class);
            DropQueryBuilder dropQueryBuilder = new H2DropQueryBuilder(Person.class);

            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            jdbcTemplate.execute(createQueryBuilder.makeQuery());
            jdbcTemplate.execute(dropQueryBuilder.makeQuery());
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
