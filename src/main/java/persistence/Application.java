package persistence;

import common.SqlParser;
import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.domain.Person;
import persistence.sql.ddl.PersistentEntity;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            PersistentEntity jpaPersistentEntity = new PersistentEntity(jdbcTemplate);
            jpaPersistentEntity.createTable(Person.class);
            jpaPersistentEntity.dropTable(Person.class);

            String sql = SqlParser.parse("src/main/java/persistence/sql/ddl/create_person.sql");
            jdbcTemplate.execute(sql);

            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        } finally {
            logger.info("Application finished");
        }
    }
}
