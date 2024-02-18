package database;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class H2 implements DatabaseServer {
    private static final Logger logger = LoggerFactory.getLogger(H2.class);
    private static final DataSourceProperties DEFAULT_PROPERTIES =
            new DataSourceProperties("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");

    private final Server server;
    private final DataSourceProperties properties;

    public H2() throws SQLException {
        this(DEFAULT_PROPERTIES);
    }

    public H2(final Server server) {
        this(server, DEFAULT_PROPERTIES);
    }

    public H2(final DataSourceProperties properties) throws SQLException {
        this(Server.createWebServer("-webPort", Integer.toString(randomPort())), properties);
    }

    public H2(final Server server, final DataSourceProperties properties) {
        this.server = server;
        this.properties = properties;
    }

    private static int randomPort() {
        final int min = 1024;
        final int max = 65535;
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    @Override
    public void start() throws SQLException {
        logger.info("Starting H2 server...");
        logger.info("H2 server URL: {}", server.getURL());
        logger.info("H2 server username: {}, password: {}", properties.getUsername(), properties.getPassword());
        server.start();
        logger.info("H2 server started");
    }

    @Override
    public void stop() {
        logger.info("Stopping H2 server...");
        server.stop();
        logger.info("H2 server stopped");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
    }
}
