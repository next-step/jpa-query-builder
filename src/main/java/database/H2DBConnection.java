package database;

import jdbc.JdbcTemplate;

import java.sql.SQLException;

public class H2DBConnection {
    private final DatabaseServer server;

    public H2DBConnection() throws SQLException {
        this.server = new H2();
    }

    public JdbcTemplate start() throws SQLException {
        server.start();
        return new JdbcTemplate(server.getConnection());
    }

    public void stop() {
        server.stop();
    }
}
