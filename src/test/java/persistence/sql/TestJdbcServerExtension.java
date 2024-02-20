package persistence.sql;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJdbcServerExtension implements BeforeAllCallback, AfterAllCallback {
    private static final Logger logger = LoggerFactory.getLogger(TestJdbcServerExtension.class);

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;

    @Override
    public void beforeAll(final ExtensionContext extensionContext) throws Exception {
        logger.info("Starting JdbcServer");

        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @Override
    public void afterAll(final ExtensionContext extensionContext) throws Exception {
        server.stop();
        logger.info("JdbcServer is stopped");
    }

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
