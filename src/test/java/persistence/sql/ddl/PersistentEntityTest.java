package persistence.sql.ddl;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.Application;
import persistence.domain.Person;

public class PersistentEntityTest {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static JdbcTemplate jdbcTemplate;
    private static DatabaseServer server;

    @BeforeAll
    public static void setUp() {
        try {
            server = new H2();
            server.start();

            jdbcTemplate = new JdbcTemplate(server.getConnection());
        } catch (Exception e) {
            logger.error("Error occurred", e);
        }
    }

    @Test
    @DisplayName("Create Query 만들기 && Drop Query 만들기")
    void createQueryTest() {
        PersistentEntity entity = new PersistentEntity(jdbcTemplate);
        entity.createTable(Person.class);
        entity.dropTable(Person.class);
    }

    @AfterAll
    public static void afterAllTests() {
        try {
            server.stop();
        } catch (Exception e) {
            logger.error("Error occurred", e);
        }
    }

}
