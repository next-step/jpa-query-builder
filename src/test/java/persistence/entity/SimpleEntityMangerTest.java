package persistence.entity;

import database.Database;
import database.DatabaseServer;
import database.H2;
import database.SimpleDatabase;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import persistence.sql.ddl.DDLQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.DMLQueryBuilder;
import persistence.sql.model.Table;
import persistence.study.sql.ddl.Person3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleEntityMangerTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;

    private static DDLQueryBuilder ddlQueryBuilder;
    private static DMLQueryBuilder dmlQueryBuilder;

    private static EntityManager entityManager;

    @BeforeAll
    static void initialize() throws SQLException {
        server = new H2();
        server.start();

        Connection jdbcConnection = server.getConnection();
        jdbcTemplate = new JdbcTemplate(jdbcConnection);

        Connection databaseConnection = server.getConnection();
        Database database = new SimpleDatabase(databaseConnection);
        entityManager = new SimpleEntityManger(database);

        Dialect dialect = new H2Dialect();
        Table table = new Table(Person3.class);
        ddlQueryBuilder = new DDLQueryBuilder(table, dialect);
        dmlQueryBuilder = new DMLQueryBuilder(table);
    }

    @AfterAll
    static void close() {
        server.stop();
    }

    @BeforeEach
    void setUp() {
        String createTableQuery = ddlQueryBuilder.buildCreateQuery();
        jdbcTemplate.execute(createTableQuery);

        Stream<Person3> persons = createPersons();

        persons.forEach(person -> {
            String insertQuery = dmlQueryBuilder.buildInsertQuery(person);
            jdbcTemplate.execute(insertQuery);
        });
    }

    @AfterEach
    void setDown() {
        String dropQuery = ddlQueryBuilder.buildDropQuery();
        jdbcTemplate.execute(dropQuery);
    }

    private Stream<Person3> createPersons() {
        return Stream.of(
                new Person3("qwer1", 1, "email1@email.com"),
                new Person3("qwer2", 2, "email2@email.com"),
                new Person3("qwer3", 3, "email3@email.com")
        );
    }

    @DisplayName("person을 이용하여 find 메서드 테스트")
    @ParameterizedTest
    @MethodSource
    void find(Object id, Object person) {
        Person3 result = entityManager.find(Person3.class, id);

        assertThat(result).isEqualTo(person);
    }

    private static Stream<Arguments> find() {
        Person3 person1 = new Person3(1L, "qwer1", 1, "email1@email.com");
        Person3 person2 = new Person3(2L, "qwer2", 2, "email2@email.com");
        Person3 person3 = new Person3(3L, "qwer3", 3, "email3@email.com");

        return Stream.of(
                Arguments.arguments(1L, person1),
                Arguments.arguments(2L, person2),
                Arguments.arguments(3L, person3)
        );
    }

    @DisplayName("person을 이용하여 persist 메서드 테스트")
    @Test
    void persist() {
        Person3 person = new Person3(null, "qwer", 1, "email@email.com");

        Person3 result = entityManager.persist(person);

        Person3 expect = new Person3(4L, "qwer", 1, "email@email.com");
        assertThat(result).isEqualTo(expect);
    }
}
