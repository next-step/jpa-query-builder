package persistence.entity;

import H2QueryBuilder.H2QueryBuilderDML;
import database.DatabaseServer;
import database.H2;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.*;
import persistence.sql.dml.Person;

import java.sql.SQLException;

import static H2QueryBuilder.fixtures.BuilderDMLFixtures.완벽한_사람_객체;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static persistence.fixtures.QueryExecutor.create;
import static persistence.fixtures.QueryExecutor.insert;

class EntityManagerTest {
    private DatabaseServer server;

    @BeforeEach
    void before() throws SQLException {
        server = new H2();
        server.start();
        create(Person.class, new JdbcTemplate(server.getConnection()));
    }

    @AfterEach
    void after() {
        server.stop();
    }

    @Test
    @DisplayName(" Person Entity 조회 ")
    void findPersonHappyCaseTest() throws SQLException {
        //given
        Person person = 완벽한_사람_객체(1L, "장장이", 22, "qwerty@naver.com", 1);
        insert(person, new JdbcTemplate(server.getConnection()));
        EntityManager em = new EntityManager(server.getConnection());

        //when
        Person findPerson = em.find(Person.class, person.getId());

        //then
        assertAll(
                () -> assertThat(findPerson.getId()).isEqualTo(person.getId()),
                () -> assertThat(findPerson.getName()).isEqualTo(person.getName())
        );
    }

}