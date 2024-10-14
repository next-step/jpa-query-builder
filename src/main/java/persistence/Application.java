package persistence;

import common.SqlParser;
import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            String createPersonSql = SqlParser.parse("src/main/java/persistence/sql/ddl/create_person_v2.sql");
            String createUserSql = SqlParser.parse("src/main/java/persistence/sql/ddl/create_users.sql");

            String dropPersonSql = SqlParser.parse("src/main/java/persistence/sql/ddl/drop_person.sql");
            String dropUserSql = SqlParser.parse("src/main/java/persistence/sql/ddl/drop_users.sql");

            jdbcTemplate.execute(createPersonSql);
            jdbcTemplate.execute(createUserSql);
            jdbcTemplate.execute(dropPersonSql);
            jdbcTemplate.execute(dropUserSql);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
