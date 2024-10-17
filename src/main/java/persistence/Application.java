package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateTableQueryBuilder;
import persistence.sql.ddl.DropTableQueryBuilder;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.dml.DeleteQueryBuilder;
import persistence.sql.dml.InsertQueryBuilder;
import persistence.sql.dml.SelectQueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            QueryBuilder ddlQueryBuilder = new CreateTableQueryBuilder();

            String createTableQuery = ddlQueryBuilder.executeQuery(Person.class);
            jdbcTemplate.execute(createTableQuery); // Create table

            Person person = Person.of(null, "2xample", 30, "2xample.gmail.com", null);

            InsertQueryBuilder insertQueryBuilder = new InsertQueryBuilder();
            String insertQuery = insertQueryBuilder.insert(person);
            jdbcTemplate.execute(insertQuery); // Insert data

            SelectQueryBuilder selectQueryBuilder = new SelectQueryBuilder();
            String selectQuery = selectQueryBuilder.findAll(Person.class);
            jdbcTemplate.execute(selectQuery); // Select data

            String findOne = selectQueryBuilder.findById(Person.class, 1L);
            jdbcTemplate.execute(findOne); // Select data

            DeleteQueryBuilder deleteQueryBuilder = new DeleteQueryBuilder();
            deleteQueryBuilder.deleteById(Person.class, 1L);
            jdbcTemplate.execute(deleteQueryBuilder.deleteById(Person.class, 1L)); // Delete data

            ddlQueryBuilder = new DropTableQueryBuilder();
            String dropTableQuery = ddlQueryBuilder.executeQuery(Person.class);

            jdbcTemplate.execute(dropTableQuery); // Drop table

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
