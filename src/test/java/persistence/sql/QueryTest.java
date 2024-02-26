package persistence.sql;

import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.sql.ddl.DDLQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.dml.DMLQueryBuilder;
import persistence.sql.model.Table;
import persistence.study.sql.ddl.Person3;
import persistence.study.sql.ddl.Person3RowMapper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private static DDLQueryBuilder ddlQueryBuilder;
    private static DMLQueryBuilder dmlQueryBuilder;

    @BeforeAll
    static void initialize() throws SQLException {
        server = new H2();
        server.start();

        Connection connection = server.getConnection();
        jdbcTemplate = new JdbcTemplate(connection);

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
    void setUp() throws SQLException {



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

    @Test
    @DisplayName("findAll 쿼리 통합 테스트")
    void findAllQuery() {
        String findAllQuery = dmlQueryBuilder.buildFindAllQuery();
        List<Person3> findPersons = jdbcTemplate.query(findAllQuery, new Person3RowMapper());

        Person3 expectPerson1 = new Person3(1L, "qwer1", 1, "email1@email.com");
        Person3 expectPerson2 = new Person3(2L, "qwer2", 2, "email2@email.com");
        Person3 expectPerson3 = new Person3(3L, "qwer3", 3, "email3@email.com");

        assertThat(findPersons).containsExactlyInAnyOrder(expectPerson1, expectPerson2, expectPerson3);
    }

    @Test
    @DisplayName("findById 쿼리 통합 테스트")
    void findByIdQuery() {
        Person3 person = new Person3(3L, "qwer3", 3, "email3@email.com");

        String findByIdQuery = dmlQueryBuilder.buildFindByIdQuery(person);
        Person3 result = jdbcTemplate.queryForObject(findByIdQuery, new Person3RowMapper());

        assertThat(result).isEqualTo(person);
    }

    @Test
    @DisplayName("delete 쿼리 통합 테스트")
    void deleteQuery() {
        String deleteQuery = dmlQueryBuilder.buildDeleteQuery();
        jdbcTemplate.execute(deleteQuery);

        String findAllQuery = dmlQueryBuilder.buildFindAllQuery();
        List<Person3> persons = jdbcTemplate.query(findAllQuery, new Person3RowMapper());

        assertThat(persons).isEmpty();
    }
}
