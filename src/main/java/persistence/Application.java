package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.Person;
import persistence.sql.ddl.QueryGenerator;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        new Application().run();
    }

    private void run() {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            final QueryGenerator queryGenerator = new QueryGenerator();
            final String sql = queryGenerator.create(Person.class);
            jdbcTemplate.execute(sql);

            jdbcTemplate.verifyTableCreation(Person.class);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
            throw new RuntimeException(e);
        } finally {
            logger.info("Application finished");
        }
    }
}
