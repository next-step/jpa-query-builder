package database;

import jdbc.JdbcTemplate;

import java.sql.SQLException;

public class H2DBConnection {

    private DatabaseServer server;

    public JdbcTemplate start() throws SQLException {
        this.server = new H2();
        server.start();

        return new JdbcTemplate(server.getConnection());
    }

    public void stop() {
        server.stop();
    }
}
