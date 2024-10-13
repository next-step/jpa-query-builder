package persistence;

import builder.QueryBuilderDDL;
import builder.h2.H2QueryBuilderDDL;
import database.DatabaseServer;
import database.H2;
import entity.Person;
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
            final QueryBuilderDDL queryBuilderDDL = new H2QueryBuilderDDL();
            logger.debug(queryBuilderDDL.buildCreateQuery(Person.class));
            jdbcTemplate.execute(queryBuilderDDL.buildCreateQuery(Person.class));
            logger.debug(queryBuilderDDL.buildDropQuery(Person.class));
            jdbcTemplate.execute(queryBuilderDDL.buildDropQuery(Person.class));

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
