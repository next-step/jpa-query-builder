package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.QueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.Builder(jdbcTemplate);
            logger.debug(jdbcTemplate.execute("SELECT * FROM users"));
            queryBuilder.Dropper(jdbcTemplate);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}

//CREATE TABLE Person (id BIGINT PRIMARY KEY AUTO_INCREMENT, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL);