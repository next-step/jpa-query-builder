package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.core.EntityMetadata;
import persistence.sql.ddl.DdlGenerator;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            final DdlGenerator generator = new DdlGenerator();

            final EntityMetadata<Person> personEntityMetadata = new EntityMetadata<>(Person.class);

            final String createDdl = generator.generateCreateDdl(personEntityMetadata);
            jdbcTemplate.execute(createDdl);
            logger.info(createDdl);
            final String dropDdl = generator.generateDropDdl(personEntityMetadata);
            jdbcTemplate.execute(dropDdl);
            logger.info(dropDdl);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
