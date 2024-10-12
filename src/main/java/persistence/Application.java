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
        CreateDDLGenerator createDDLGenerator = new H2CreateDDLGenerator();
        DropDDLGenerator dropDDLGenerator = new H2DropDDLGenerator();

        Entity entity = Entity.of(Person.class);

        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            String createDDL = createDDLGenerator.generate(entity);
            String dropDDL = dropDDLGenerator.generate(entity);

            jdbcTemplate.execute(createDDL);
            jdbcTemplate.execute(dropDDL);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
