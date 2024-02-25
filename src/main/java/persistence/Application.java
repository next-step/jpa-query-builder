package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.domain.Person;
import persistence.sql.ddl.builder.CreateQueryBuilder;
import persistence.sql.ddl.builder.DropQueryBuilder;
import persistence.sql.ddl.builder.QueryBuilder;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.H2Dialect;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            final Dialect dialect = new H2Dialect();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            QueryBuilder createQueryBuilder = new CreateQueryBuilder(dialect);
            QueryBuilder dropQueryBuilder = new DropQueryBuilder(dialect);


            jdbcTemplate.execute(createQueryBuilder.generateSQL(Person.class));
            jdbcTemplate.execute(dropQueryBuilder.generateSQL(Person.class));
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
