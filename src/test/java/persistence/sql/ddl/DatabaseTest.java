package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public class DatabaseTest {
    protected DatabaseServer server = null;
    protected JdbcTemplate jdbcTemplate = null;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @AfterEach
    void close() {
        server.stop();
    }
}
