package config;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.Application;

import java.util.function.Consumer;

public class PluggableH2test {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public void runInH2Db(Consumer<JdbcTemplate> jdbcTemplateConsumer) {
        try {
            final DatabaseServer server = new H2();
            server.start();

            final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
            jdbcTemplateConsumer.accept(jdbcTemplate);
            jdbcTemplate.execute("DROP ALL OBJECTS");
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
            throw new RuntimeException(e);
        } finally {
            logger.info("Application finished");
        }
    }
}
