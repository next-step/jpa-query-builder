package persistence;

import database.DatabaseServer;
import database.H2;
import entity.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.QueryManagerFactory;
import persistence.sql.ddl.DefinitionQueryManager;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            final DefinitionQueryManager definitionQueryManager = QueryManagerFactory.INSTANCE.definition(Person.class);

            definitionQueryManager.createQuery().forEach(jdbcTemplate::execute);
            definitionQueryManager.dropQuery().forEach(jdbcTemplate::execute);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
