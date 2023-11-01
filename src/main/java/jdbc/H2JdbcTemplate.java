package jdbc;

import database.DatabaseServer;
import database.H2;

import java.sql.Connection;
import java.sql.SQLException;

public class H2JdbcTemplate {

    private final JdbcTemplate jdbcTemplate;
    private final DatabaseServer server;


    private H2JdbcTemplate() {
        try {
            this.server = new H2();
            this.server.start();
            this.jdbcTemplate = new JdbcTemplate(server.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
