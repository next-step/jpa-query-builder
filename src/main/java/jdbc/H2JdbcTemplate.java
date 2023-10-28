package jdbc;

import database.DatabaseServer;
import database.H2;

import java.sql.Connection;
import java.sql.SQLException;

public class H2JdbcTemplate extends JdbcTemplate {


    private H2JdbcTemplate(final Connection connection) {
        super(connection);
    }

    public static JdbcTemplate getInstance() {
        try {
            final DatabaseServer server = new H2();
            server.start();
            return new JdbcTemplate(server.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
