package persistence.sql.entity;

import database.DatabaseServer;
import database.H2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.TestJdbcTemplate;
import persistence.sql.ddl.DdlQueryBuilder;
import persistence.sql.ddl.H2Dialect;
import persistence.sql.ddl.Person;
import persistence.sql.dml.DmlQueryBuilder;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class H2EntityManagerTest {
    private DatabaseServer server;
    private Connection connection;
    private TestJdbcTemplate jdbcTemplate;
    private DdlQueryBuilder ddlQueryBuilder;
    private DmlQueryBuilder dmlQueryBuilder;
    private H2EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        server = new H2();
        server.start();
        connection = server.getConnection();
        jdbcTemplate = new TestJdbcTemplate(connection);
        ddlQueryBuilder = new DdlQueryBuilder(new H2Dialect());
        dmlQueryBuilder = new DmlQueryBuilder();
        createTableAndVerify();
        entityManager = new H2EntityManager(jdbcTemplate, dmlQueryBuilder);
    }

    @DisplayName("Person 객체를 저장한다.")
    @Test
    void persist() {
        final Person person = new Person(1L, "Kent Beck", 64, "beck@example.com");
        assertDoesNotThrow(() -> entityManager.persist(person));
    }

    private void createTableAndVerify() {
        createTable();
        assertTableCreated();
    }

    private void assertTableCreated() {
        assertTrue(jdbcTemplate.doesTableExist(Person.class), "Table was not created.");
    }

    private void createTable() {
        final String createSql = ddlQueryBuilder.create(Person.class);
        jdbcTemplate.execute(createSql);
    }
}
