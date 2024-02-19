package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.*;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            DdlQueryBuilderFactory factory = new DdlQueryBuilderFactory();

            DdlQueryBuilder ddlQueryBuilder = factory.getInstance(DatabaseDialect.MYSQL);

            jdbcTemplate.execute(ddlQueryBuilder.createQuery(Person.class));
            jdbcTemplate.execute(ddlQueryBuilder.dropQuery(Person.class));

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
