package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.ddl.DropQueryBuilder;
import persistence.sql.dialect.DbmsQueryBuilder;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            DbmsQueryBuilder queryBuilder = DbmsQueryBuilder.findByDbmsType(H2.class.getSimpleName());

            CreateQueryBuilder createQueryBuilder = queryBuilder.getCreateQueryBuilder();
            jdbcTemplate.execute(createQueryBuilder.getQuery(Person.class));

            DropQueryBuilder dropQueryBuilder = queryBuilder.getDropQueryBuilder();
            jdbcTemplate.execute(dropQueryBuilder.getQuery(Person.class));

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
