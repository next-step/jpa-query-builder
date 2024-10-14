package persistence.sql.dml;

import database.DatabaseServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.QueryBuilderFactory;
import persistence.sql.config.PersistenceConfig;
import persistence.sql.data.QueryType;
import persistence.sql.ddl.TableScanner;
import persistence.sql.dml.impl.SimpleMetadataLoader;
import persistence.sql.node.EntityNode;

import java.util.Set;

public class TestEntityInitialize {
    private static final Logger logger = LoggerFactory.getLogger(TestEntityInitialize.class);
    static DatabaseServer server;

    @BeforeAll
    static void init() {
        try {
            PersistenceConfig config = PersistenceConfig.getInstance();
            Database database = config.database();
            server = config.databaseServer();
            server.start();

            TableScanner tableScanner = config.tableScanner();
            Set<EntityNode<?>> nodes = tableScanner.scan("persistence.sql.fixture");

            QueryBuilderFactory factory = QueryBuilderFactory.getInstance();
            for (EntityNode<?> node : nodes) {
                String createTableQuery = factory.buildQuery(QueryType.CREATE,
                        new SimpleMetadataLoader<>(node.entityClass()), null);
                database.executeUpdate(createTableQuery);
            }
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }

    @AfterAll
    static void destroy() {
        try {
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        }
    }
}
