package persistence;

import database.DatabaseServer;
import database.H2;
import persistence.sql.ddl.DropQueryBuilderDDL;
import persistence.sql.ddl.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.QueryBuilderDDL;
import persistence.sql.ddl.QueryBuilderDDL3;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            String sql = new DropQueryBuilderDDL(Person.class).makeQuery();
            jdbcTemplate.execute(sql);
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
