package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.QueryBuilderFactory;
import persistence.sql.data.QueryType;
import persistence.sql.ddl.TableScanner;
import persistence.sql.config.PersistenceConfig;
import persistence.sql.dml.impl.SimpleMetadataLoader;
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
            PersistenceConfig persistenceConfig = PersistenceConfig.getInstance();

            TableScanner tableScanner = persistenceConfig.tableScanner();
            Set<EntityNode<?>> nodes = tableScanner.scan(BASE_PACKAGE);

            QueryBuilderFactory factory = QueryBuilderFactory.getInstance();
            for (EntityNode<?> node : nodes) {
                String createTableQuery = factory.buildQuery(QueryType.CREATE, new SimpleMetadataLoader<>(node.entityClass()), null);
                logger.info("Create table query: {}", createTableQuery);
                jdbcTemplate.execute(createTableQuery);
            }

            for (EntityNode<?> node : nodes) {
                String dropTableQuery = factory.buildQuery(QueryType.DROP, new SimpleMetadataLoader<>(node.entityClass()), null);
                logger.info("drop table query: {}", dropTableQuery);
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
