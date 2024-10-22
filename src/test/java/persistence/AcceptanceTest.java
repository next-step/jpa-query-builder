package persistence;

import database.DatabaseServer;
import database.H2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.H2Dialect;
import persistence.sql.ddl.Person;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AcceptanceTest {
    private DatabaseServer server;
    private TestJdbcTemplate jdbcTemplate;
    private DdlQueryBuilder ddlQueryBuilder;

    @BeforeEach
    void setUp() throws Exception {
        server = new H2();
        server.start();
        Connection connection = server.getConnection();
        jdbcTemplate = new TestJdbcTemplate(connection);
        ddlQueryBuilder = new DdlQueryBuilder(new H2Dialect());
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @DisplayName("시나리오 테스트")
    @Test
    void scenario() {
        createTable();
        assertTableCreated();

        deleteTable();
        assertTableDeleted();
    }

    private void assertTableDeleted() {
        assertFalse(jdbcTemplate.doesTableExist(Person.class), "Table was not deleted.");
    }

    private void deleteTable() {
        String dropSql = ddlQueryBuilder.drop(Person.class);
        jdbcTemplate.execute(dropSql);
    }

    private void assertTableCreated() {
        assertTrue(jdbcTemplate.doesTableExist(Person.class), "Table was not created.");
    }

    private void createTable() {
        String createSql = ddlQueryBuilder.create(Person.class);
        jdbcTemplate.execute(createSql);
    }
}