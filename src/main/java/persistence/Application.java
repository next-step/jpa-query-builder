package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateTableQueryBuilder;
import persistence.sql.ddl.DropTableQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            CreateTableQueryBuilder createTableQueryBuilder = new CreateTableQueryBuilder();
            String createTableQuery = createTableQueryBuilder.createTable(Person.class);
            logger.info(createTableQuery);
            jdbcTemplate.execute(createTableQuery); // Create table

            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
            Person person = new Person();
            person.setName("2xample");
            person.setAge(30);
            person.setEmail("2xample.gmail.com");
            String insertQuery = insertQueryBuilder.insert(person);
            logger.info(insertQuery);
            jdbcTemplate.execute(insertQuery); // Insert data

            DropTableQueryBuilder ddlQueryBuilder = new DropTableQueryBuilder();
            String dropTableQuery = DropTableQueryBuilder.dropTable( Person.class);
            logger.info(dropTableQuery);

            jdbcTemplate.execute(dropTableQuery); // Drop table

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
