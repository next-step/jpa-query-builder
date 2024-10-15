package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.dialect.DialectFactory;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");

        DdlQueryBuilder queryBuilder = new DdlQueryBuilder(DialectFactory.create(H2.class));

        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            jdbcTemplate.execute(queryBuilder.buildCreateTableQuery(ExampleEntity.class));
            jdbcTemplate.execute(queryBuilder.buildDropTableQuery(ExampleEntity.class));

//            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
