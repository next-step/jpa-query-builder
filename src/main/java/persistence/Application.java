package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateDDLQueryBuilder;
import persistence.sql.ddl.DDLQueryBuilder;
import persistence.sql.ddl.H2TypeConverter;
import persistence.sql.ddl.TypeConverter;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final TypeConverter typeConverter = new H2TypeConverter();
            final DDLQueryBuilder queryBuilder = new CreateDDLQueryBuilder(typeConverter);
            final String createQuery = queryBuilder.query(Person.class);

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            jdbcTemplate.execute(createQuery);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
