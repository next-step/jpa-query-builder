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
import persistence.sql.dml.DmlQueryBuilder;

import java.sql.Connection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AcceptanceTest {
    private DatabaseServer server;
    private TestJdbcTemplate jdbcTemplate;
    private DdlQueryBuilder ddlQueryBuilder;
    private DmlQueryBuilder dmlQueryBuilder;

    @BeforeEach
    void setUp() throws Exception {
        server = new H2();
        server.start();
        Connection connection = server.getConnection();
        jdbcTemplate = new TestJdbcTemplate(connection);
        ddlQueryBuilder = new DdlQueryBuilder(new H2Dialect());
        dmlQueryBuilder = new DmlQueryBuilder(new H2Dialect());
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

        final Person kentBeck = new Person("Kent Beck", 64, "beck@example.com");
        final Person martinFowler = new Person("Martin Fowler", 62, "martin@example.com");
        insert(kentBeck, martinFowler);

        final List<Person> people = findAll(dmlQueryBuilder.select(Person.class));
        assertInsertion(people);

        deleteTable();
        assertTableDeleted();
    }

    private void assertInsertion(final List<Person> people) {
        assertThat(people).hasSize(2);
    }

    private List<Person> findAll(final String select) {
        return jdbcTemplate.query(select, resultSet -> {
            final String nickName = resultSet.getString("nick_name");
            final int old = resultSet.getInt("old");
            final String email = resultSet.getString("email");
            return new Person(nickName, old, email);
        });
    }

    private void insert(final Person person1, final Person person2) {
        jdbcTemplate.execute(dmlQueryBuilder.insert(Person.class, person1));
        jdbcTemplate.execute(dmlQueryBuilder.insert(Person.class, person2));
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
