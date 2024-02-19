package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.entity.Person1;
import persistence.sql.ddl.StandardDialectResolver;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.ddl.dialect.DialectResolutionInfo;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            DialectResolutionInfo dialectResolutionInfo = new DialectResolutionInfo(server.getConnection().getMetaData());
            Dialect dialect = StandardDialectResolver.resolveDialect(dialectResolutionInfo);

            String createTableSql = dialect.createTable(Person1.class);

            logger.debug(createTableSql);
            jdbcTemplate.execute(createTableSql);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
