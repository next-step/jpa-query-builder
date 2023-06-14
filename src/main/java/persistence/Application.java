package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.ddl.CreateTableBuilder;
import persistence.ddl.DeleteTableBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            EntityScanner entityScanner = new EntityScanner(Person.class);
            Table table = entityScanner.table();
            Columns columns = entityScanner.columns();

            CreateTableBuilder createTableBuilder = new CreateTableBuilder(table, columns);
            DeleteTableBuilder deleteTableBuilder = new DeleteTableBuilder(table);

            jdbcTemplate.execute(createTableBuilder.query());
            jdbcTemplate.execute(deleteTableBuilder.query());

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
