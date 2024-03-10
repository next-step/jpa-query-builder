package persistence.entity;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.CreateQueryBuilder;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleEntityManagerTest {

    private JdbcTemplate jdbcTemplate;
    private EntityManager entityManager;
    private Dialect DIALECT = new H2Dialect();
    private DatabaseServer server;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();
        jdbcTemplate = new JdbcTemplate(server.getConnection());

        jdbcTemplate.execute(CreateQueryBuilder.builder()
                .dialect(DIALECT)
                .entity(Person.class)
                .build()
                .generateQuery());

        entityManager = new SimpleEntityManager(jdbcTemplate, DIALECT);
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    @DisplayName("요구사항1 - find")
    void find() {
        // given
        Person person = Person.of(1L, "joel", 37, "test!@gmail.com");
        entityManager.persist(person);

        // when
        Person findPerson = entityManager.find(Person.class, 1L);

        // then
        assertThat(findPerson).isEqualTo(person);
    }

}