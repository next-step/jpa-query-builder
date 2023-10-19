package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.H2Dialect;
import persistence.sql.ddl.TableCreateQueryBuilder;
import persistence.sql.ddl.TableDropQueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            String sql = new TableCreateQueryBuilder(new H2Dialect()).generateSQLQuery(Person.class);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            sql = new TableDropQueryBuilder(new H2Dialect()).generateSQLQuery(Person.class);
            logger.info(sql);
            jdbcTemplate.execute(sql);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
