package persistence;

import database.DatabaseServer;
import database.H2;
import domain.Person;
import java.sql.SQLException;
import jdbc.JdbcTemplate;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.sql.ddl.DdlGenerator;
import persistence.sql.dialect.h2.H2Dialect;
import persistence.sql.dml.DmlGenerator;

@DisplayName("SimpleEntityManager class 의")
class SimpleEntityManagerTest {

    private DatabaseServer server;

    private JdbcTemplate jdbcTemplate;
    private DdlGenerator ddlGenerator;
    private DmlGenerator dmlGenerator;
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws SQLException {
        server = new H2();
        server.start();

        jdbcTemplate = new JdbcTemplate(server.getConnection());
        ddlGenerator = DdlGenerator.from(H2Dialect.getInstance());
        dmlGenerator = DmlGenerator.from();
        entityManager = SimpleEntityManager.from(jdbcTemplate);
        jdbcTemplate.execute(ddlGenerator.generateCreateQuery(Person.class));
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute(ddlGenerator.generateDropQuery(Person.class));
        server.stop();
    }

    @DisplayName("find 메서드는")
    @Nested
    class Find {

        @DisplayName("Person entity를 검색 할 수 있다.")
        @Test
        void findTest() {
            // given
            Person person = Person.of("user1", 1, "abc@gtest.com", 1);
            jdbcTemplate.execute(dmlGenerator.generateInsertQuery(person));

            // when
            Person foundPerson = entityManager.find(Person.class, 1L);

            // then
            assertAll(
                () -> assertEquals(person.getName(), foundPerson.getName()),
                () -> assertEquals(person.getAge(), foundPerson.getAge()),
                () -> assertEquals(person.getEmail(), foundPerson.getEmail())
            );
        }

    }
}
