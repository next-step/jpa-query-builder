package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.dml.Person;
import persistence.sql.dml.QueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            QueryBuilder queryBuilder = new QueryBuilder(Person.class, jdbcTemplate);

            jdbcTemplate.execute("CREATE TABLE USERS (id BIGINT AUTO_INCREMENT PRIMARY KEY, nick_name VARCHAR(255), old INTEGER, email VARCHAR(255) NOT NULL)");
            queryBuilder.run(jdbcTemplate);
            queryBuilder.findAll(jdbcTemplate);
            queryBuilder.findById(jdbcTemplate, 1L);
            queryBuilder.deleteById(jdbcTemplate, 1L);
            jdbcTemplate.execute("SELECT * FROM USERS");

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
