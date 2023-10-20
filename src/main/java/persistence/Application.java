package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.EntityMetaData;
import persistence.sql.ddl.Person;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            DdlQueryBuilder ddlQueryBuilder = DdlQueryBuilder.getInstance();
            EntityMetaData entityMetaData = new EntityMetaData(Person.class);
            jdbcTemplate.execute(ddlQueryBuilder.createTable(entityMetaData));
            jdbcTemplate.execute(ddlQueryBuilder.dropTable(entityMetaData));
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
