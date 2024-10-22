package persistence;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.H2Dialect;
import persistence.sql.ddl.Person;

import java.sql.SQLException;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(final String[] args) {
        new Application().run();
    }

    private void run() {
        logger.info("Starting application...");
        DatabaseServer server = null;
        try {
            server = setUp();
            executeQueries(server);
        } catch (final Exception e) {
            handleException(e);
        } finally {
            cleanUp(server);
        }
        logger.info("Application finished");
    }

    private DatabaseServer setUp() throws SQLException {
        final DatabaseServer server;
        server = new H2();
        server.start();
        return server;
    }

    private void executeQueries(final DatabaseServer server) throws SQLException {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
        final DdlQueryBuilder ddlQueryBuilder = new DdlQueryBuilder(new H2Dialect());
        final String createSql = ddlQueryBuilder.create(Person.class);
        jdbcTemplate.execute(createSql);
        final String dropSql = ddlQueryBuilder.drop(Person.class);
        jdbcTemplate.execute(dropSql);
    }

    private void handleException(final Exception e) {
        logger.error("Error occurred", e);
        throw new RuntimeException(e);
    }

    private void cleanUp(final DatabaseServer server) {
        server.stop();
    }
}
