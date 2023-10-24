package persistence.testutils;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public abstract class TestQueryExecuteSupport {

    protected static DatabaseServer server;
    protected static JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void beforeAll() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
    }

    @AfterAll
    static void afterAll() {
        server.stop();
    }
}
