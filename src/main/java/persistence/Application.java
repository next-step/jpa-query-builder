package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.QueryBuilderHelper;
import persistence.sql.ddl.TableScanner;
import persistence.sql.ddl.impl.AnnotatedTableScanner;
import persistence.sql.ddl.impl.H2QueryBuilder;
import persistence.sql.ddl.node.EntityNode;

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

            TableScanner tableScanner = new AnnotatedTableScanner();
            Set<EntityNode<?>> nodes = tableScanner.scan(BASE_PACKAGE);

            QueryBuilderHelper queryBuilder = new H2QueryBuilder();
            for (EntityNode<?> node : nodes) {
                String createTableQuery = queryBuilder.createTableQuery(node);
                logger.info("Create table query: {}", createTableQuery);
                jdbcTemplate.execute(createTableQuery);
            }

            //server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
