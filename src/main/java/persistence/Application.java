package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.domain.Person;
import persistence.sql.ddl.h2.builder.CreateQueryBuilder;
import persistence.sql.ddl.h2.builder.DropQueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            jdbcTemplate.execute(new CreateQueryBuilder(Person.class).generateSQL());
            jdbcTemplate.execute(new DropQueryBuilder(Person.class).generateSQL());
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
