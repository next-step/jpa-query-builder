package persistence.entity;

import database.DatabaseServer;
import database.H2;
import entity.Person;
import fixture.PersonFixture;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.sql.ddl.CreateQueryBuilder;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleEntityManagerIntegrationTest {

    private static DatabaseServer server;
    private static JdbcTemplate jdbcTemplate;
    private static SimpleEntityManager manager;

    @BeforeAll
    static void setUp() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        String query = new CreateQueryBuilder().getQuery(Person.class);
        jdbcTemplate.execute(query);
        manager = new SimpleEntityManager(jdbcTemplate);

    }

    @AfterAll
    static void close() {
        List<String> tableNames = jdbcTemplate.query("SHOW TABLES", (resultSet -> resultSet.getString(1)));
        for (String tableName : tableNames) {
            jdbcTemplate.execute("DROP TABLE " + tableName);
        }
        server.stop();
    }

    @DisplayName("find 테스트")
    @Test
    void find() {
        //given
        manager.persist(PersonFixture.changgunyee());

        //when
        Person person = manager.find(Person.class, 1L);

        //then
        assertThat(person.getId()).isEqualTo(PersonFixture.changgunyee().getId());
    }


    @DisplayName("remove 테스트")
    @Test
    void remove() {
        //given
        Person changgunyee = PersonFixture.changgunyee();
        manager.persist(changgunyee);

        //when
        manager.remove(changgunyee);

        //then
        Person found = manager.find(Person.class, changgunyee.getId());
        assertThat(found).isNull();
    }
}