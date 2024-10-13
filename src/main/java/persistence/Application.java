package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.TableScanner;
import persistence.sql.ddl.config.PersistenceDDLConfig;
import persistence.sql.node.EntityNode;

import java.util.Set;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static final String BASE_PACKAGE = "sample.domain";

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            PersistenceDDLConfig persistenceDDLConfig = PersistenceDDLConfig.getInstance();

            TableScanner tableScanner = persistenceDDLConfig.tableScanner();
            Set<EntityNode<?>> nodes = tableScanner.scan(BASE_PACKAGE);

            QueryBuilder queryBuilder = persistenceDDLConfig.queryBuilder();
            for (EntityNode<?> node : nodes) {
                String createTableQuery = queryBuilder.buildCreateTableQuery(node);
                logger.info("Create table query: {}", createTableQuery);
                jdbcTemplate.execute(createTableQuery);
            }

            for (EntityNode<?> node : nodes) {
                String dropTableQuery = queryBuilder.buildDropTableQuery(node);
                logger.info("Create table query: {}", dropTableQuery);
                jdbcTemplate.execute(dropTableQuery);
            }


            //server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
