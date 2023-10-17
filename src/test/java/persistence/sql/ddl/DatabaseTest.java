package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class DatabaseTest {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseTest.class);

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
        List<String> tableNames = jdbcTemplate.query("SHOW TABLES", new StringRowMapper());

        for (String tableName : tableNames) {
            logger.info("Starting DROP TABLE");
            jdbcTemplate.execute("DROP TABLE " + tableName);
        }

        server.stop();
    }
}
