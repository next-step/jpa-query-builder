package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.core.EntityMetadataModelFactory;
import persistence.core.EntityMetadataModelHolder;
import persistence.core.EntityMetadataModels;
import persistence.sql.JdbcTypeJavaClassMapping;
import persistence.sql.ddl.TableDdlQueryBuilder;
import persistence.sql.dml.DmlGenerator;

import java.util.List;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());

            EntityMetadataModelFactory entityMetadataModelFactory = new EntityMetadataModelFactory();
            EntityMetadataModels entityMetadataModels = entityMetadataModelFactory.createEntityMetadataModels(List.of(Person.class));

            TableDdlQueryBuilder h2TableDdlQueryBuilder = new TableDdlQueryBuilder(new JdbcTypeJavaClassMapping());
            String createTableQuery = h2TableDdlQueryBuilder.createDdlQuery(entityMetadataModels.getMetadataModels()
                    .stream()
                    .findFirst()
                    .orElseThrow());

            jdbcTemplate.execute(createTableQuery);

            DmlGenerator dmlGenerator = new DmlGenerator(new EntityMetadataModelHolder(entityMetadataModels));

            jdbcTemplate.queryForObject(dmlGenerator.findAll(Person.class), resultSet -> null);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
